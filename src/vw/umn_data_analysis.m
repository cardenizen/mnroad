function umn_data_analysis(varargin)
clc;
single_file = 1;
sensor_type = 0;
tic;
MAX_VALID_READING = 500;
VW = 1;
HC = 2;
TC = 3;
XV = 4;
% Number of standard deviations above/below which a point is declared an
% outlier
n_dev = 0;

% If the number of arguments is not 5 or 6 then return
if nargin ~= 5 && nargin ~= 6
    disp('5 or 6 Parameters needed');
    disp('Parameter 1: VW or HC or XV or TC');
    disp('Parameter 2: all or sensor input file name');
    disp('Parameter 3: all or sensor number');
    disp('Parameter 4: Input files directory');
    disp('Parameter 5: Output files directory');
    disp('Parameter 6: Number of standard deviations');
    disp('Please try again with correct number of parameters (default is 3)');
    return;
end
% Figure out which sensor needs to be analyzed
if(strcmp(varargin{1},'VW' ))
    sensor_type = VW;
elseif(strcmp(varargin{1},'HC' ))
    sensor_type = HC;
elseif(strcmp(varargin{1},'TC' ))
    sensor_type = TC;
elseif(strcmp(varargin{1},'XV' ))
    sensor_type = XV;    
else
    disp('First parameter should be  VW or HC or XV or TC');
    return;
end

% Figure out if a single file needs to be analyzed or all files in the
% input directory need to be analyzed
compare_string = varargin{2};
if(strcmp(compare_string,'all' ))
    single_file = 0;
    if(sensor_type == VW)
        compare_string = '*_VW.csv';
        t_compare_string = '*_XV.csv';
    elseif(sensor_type == HC)
        compare_string = '*_HC.csv';        
    elseif(sensor_type == TC)
        compare_string = '*_TC.csv';        
    elseif(sensor_type == XV)
        compare_string = '*_XV.csv';        
    else
       disp('Invalid sensor type');
       return;
    end
end

curpath = cd;
input_dir = varargin{4};
output_dir = varargin{5};

if nargin == 5
    n_dev = 3;
else
    n_dev = varargin{6};
end

% Check if input directory exists
if(isdir(input_dir) == 0)
    disp('Input directory invalid');
    disp('Try again with valid directory');
    return;
end

% Check if output directory exists
if(isdir(output_dir) == 0)
    disp('Output directory invalid');
    disp('Try again with valid directory');
    return;
end

if(single_file == 0)
    cd(input_dir);
    fvw = dir(compare_string);
    if(sensor_type == VW)
        fxv = dir(t_compare_string);
    end
    cd(curpath);
else
    if(sensor_type == VW)
        t_compare_string = strcat(compare_string(1:end-7),'_XV.csv');
    end    
end

% Figure out if all sensors need to be analyzed or only one particular
% sensor needs to be analyzed
sensor_pattern = varargin{3};
if(strcmp(sensor_pattern,'all') == 0)
    start_sensor = str2num(varargin{3});
    end_sensor = str2num(varargin{3});
end

if(single_file == 0)
    end_loop = length(fvw);
else
    end_loop = 1;
end

for fnum=1:end_loop
    cd(input_dir);
    % Read the input file(s)
    if(single_file == 0)
        [num, txt, raw] = xlsread(fvw(fnum).name);
        fname = fvw(fnum).name;
        if(sensor_type == VW)
            [num_t, txt_t, raw] = xlsread(fxv(fnum).name);
        end
    else
        [num, txt, raw] = xlsread(compare_string);
        fname = compare_string;
        if(sensor_type == VW)
            [num_t, txt_t, raw] = xlsread(t_compare_string);
        end
    end
    cd(curpath);
    [row column] = size(num);
    sensor_title = txt(1,6:column);
    %If all sensors need to be analyzed go till the end
    if(strcmp(sensor_pattern,'all'))
        start_sensor = 1;
        end_sensor = column-5;
    end

    disp(sprintf('Processing file %s',fname));
    for col = start_sensor:end_sensor
        % out_filename - generate the output file name in the following
        % format
        % For example in the file '1996Cell5_XV.csv' for sensor 's_1' the
        % output file should be '1996Cell5_XV_s_1.csv'
        out_filename = sprintf('%s_%s.csv',fname(1:end-4),char(sensor_title(col)));
        disp(sprintf('Processing sensor %s',char(sensor_title(col))));
        result = cell(0);
        output = cell(length(num),9);
        output(:,7:9) = {0};
        % Read the sensor value and eliminate those that have a value > 500
        % i.e. eliminate the 99999 sensor values
        num(isnan(num(:,col+5)),col+5) = MAX_VALID_READING+1;
        out_of_bound = find(num(:,col+5)>MAX_VALID_READING);
        % numcln is sensor data with out of range values like 99999 removed
        numcln = num(setdiff(1:length(num),find(num(:,col+5)>MAX_VALID_READING)),col+5);
        % clnInd is the time indice of the clean data
        clnInd = setdiff(1:length(num),find(num(:,col+5)>MAX_VALID_READING));       
        
        % For Vibrating Wire, temperature correction needed
        if(sensor_type == VW)
           % numcln_t is temperature data with out of range values like 99999 removed
            numcln_t = num_t(setdiff(1:length(num_t),find(num_t(:,col+5)>MAX_VALID_READING)),col+5);
           % clnInd is the time indice of the clean temperature data
            clnInd_t = setdiff(1:length(num_t),find(num_t(:,col+5)>MAX_VALID_READING));       
        end
        
        if(~isempty(clnInd))
            % diff_Vw gives the difference of clean indices
            % If there is a break in the data due to intermediate out of
            % range values the difference between the indices will not be 1
            diff_Vw = diff(clnInd);
            % Vw_diff_count counts the total number of data
            % sets with continuous time indices
            Vw_diff_count = length(find(diff_Vw ~= 1));
            Vw_diff_index = find(diff_Vw ~= 1);
            Vw_diff_val = diff_Vw(Vw_diff_index);

            start = clnInd(1);
            val_start = 1;
            index = 1;
            % starting from index 1, go till the next available continuous
            % index
            for time_index = 1:Vw_diff_count+1
                order = 15;
                % If we reached the end of the data set then end_vw is
                % simply the last time index or else it is the next
                % available continuous time index
                if(time_index == Vw_diff_count+1)
                    end_vw = clnInd(end);
                    val_end = length(numcln);
                else
                    end_vw = clnInd(Vw_diff_index(time_index));
                    val_end = Vw_diff_index(time_index);
                end
                interval_complete = 0;
                reverse_start = start;
                reverse_val_start = val_start;
                %interval_complete flag indicates if we reached the end of
                %the interval. If any outlier is found, the outlier detection continues
                %from the next time index after the outlier till the end of the interval 
                while(interval_complete == 0)
                    if(order ~= 15)
                        order = 15;
                    end
                    if (val_end-val_start<order)
                        order=val_end-val_start;
                    end
                    % the data in the latest interval
                    x_new = numcln(val_start+1:val_end)- numcln(val_start:val_end-1);
                    if(isempty(x_new))
                        interval_complete = 1;
                        continue;
                    end
                    % For VW data, Temperature data should also be used for estimating                  
                    if(sensor_type == VW)
                        if(val_end <= length(numcln_t))
                            x_temp(:,1) = x_new;
                            t_new = numcln_t(val_start+1:val_end)- numcln_t(val_start:val_end-1);
                            x_temp(:,2) = t_new;
                            a = lpc(x_temp,order);
                            xfil = filter([0 -a(2:end,1)],1,x_new);
                            clear x_temp;
                        else
                            a = lpc(x_new,order);
                            xfil = filter([0 -a(2:end)],1,x_new);
                            disp('No temperature data');                            
                        end
                        xest = [xfil(2:end)' x_new(end)];   % Estimated signal
                    else
                        % a - coefficients of a 15th order linear predictor
                        a = lpc(x_new,order);
                        % xfil - coefficients from "a" filter the interval data
                        xfil = filter([0 -a(2:end)],1,x_new);
                        % estimated signal from prediction
                        xest = [xfil(2:end)' x_new(end)];   % Estimated signal
                    end
                    
                    %error calculation
                    error=x_new'-xest;
                    %mean of the error
                    me=mean(error);
                    clear pts;
                    % pts - points that lie above/below from n_dev standard
                    % deviations of the mean
                    pts=find((error>(me+n_dev*std(error)))|(error<(me-n_dev*std(error))));
                    if(isempty(pts))
                        interval_complete = 1;
                        continue;
                    end
                    % the outlier is index is stored in outlier variable
                    % the sensor output is stored in value variable
                    outlier(index) = start+pts(1);
                    value(index) = val_start+pts(1);
                    index = index+1;
                    % Once an outlier is found, the prediction starts from
                    % the next index to the end of the interval
                    start = start+pts(1)+1;
                    val_start = val_start+pts(1)+1;
                    clear x_new;
                    clear xest;
                    clear xfil;
                end

                % Applying linear prediction from the last index
                % The following code treats the last index as the first
                % index basically reverses the data set
                % Performing linear prediction on treating both sides as a
                % linear data set improves the outlier detection process
                reverse_interval_complete = 0;
                reverse_end = end_vw;
                reverse_val_end = val_end;

                while(reverse_interval_complete == 0)
                    if(order ~= 15)
                        order = 15;
                    end
                    if (reverse_val_end-reverse_val_start<order)
                        order=reverse_val_end-reverse_val_start;
                    end
                    %rev_cln clean data
                    rev_cln = numcln(reverse_val_end:-1:reverse_val_start);
                    x_new = rev_cln(2:end)- rev_cln(1:end-1);
                    if(isempty(x_new))
                        reverse_interval_complete = 1;
                        continue;
                    end
                    
                    % Use temperature data for Vibrating Wire
                    if(sensor_type == VW)
                        if(reverse_val_end <= length(numcln_t))
                            x_temp(:,1) = x_new;
                            rev_t_cln = numcln_t(reverse_val_end:-1:reverse_val_start);
                            t_new = rev_t_cln(2:end)- rev_t_cln(1:end-1);
                            x_temp(:,2) = t_new;
                            a = lpc(x_temp,order);
                            xfil = filter([0 -a(2:end,1)],1,x_new);
                            clear x_temp;
                        else
                            a = lpc(x_new,order);
                            xfil = filter([0 -a(2:end)],1,x_new);
                            disp('No temperature data');
                        end
                        xest = [xfil(2:end)' x_new(end)];   % Estimated signal
                    else
                        a = lpc(x_new,order);
                        xfil = filter([0 -a(2:end)],1,x_new);
                        xest = [xfil(2:end)' x_new(end)];   % Estimated signal
                    end
                    
                    %error calculation
                    error=x_new'-xest;
                    me=mean(error);
                    clear pts;
                    % pts - points that lie above/below from n_dev standard
                    % deviations of the mean                    
                    pts=find((error>(me+n_dev*std(error)))|(error<(me-n_dev*std(error))));
                    if(isempty(pts))
                        reverse_interval_complete = 1;
                        continue;
                    end
                    temp = reverse_end-pts(1)+1;
                    % the outlier is index is stored in outlier variable
                    % the sensor output is stored in value variable                    
                    outlier(index) = temp;
                    value(index) = reverse_val_end-pts(1)+1;
                    index = index+1;
                    % Once an outlier is found, the prediction starts from
                    % the next index to the end of the interval                    
                    reverse_end = temp-1;
                    reverse_val_end = reverse_val_end-pts(1);
                    clear x_new;
                    clear xest;
                    clear xfil;
                end
                % Once an interval is complete, start and val_start need to
                % be set to the next time index and the sensor data
                if(time_index ~= Vw_diff_count+1)
                    start = clnInd(Vw_diff_index(time_index))+Vw_diff_val(time_index);
                    val_start = Vw_diff_index(time_index)+1;
                end
            end
            if(index ~=1)
                % Plotting the data with outliers marked by 'x' symbol
                if(sensor_type == VW)
                    title_string = sprintf('Cell no: %d Year: %s Sensor: %s',num(1,1),fname(1:4),char(sensor_title(col)));                    
                    subplot(2,1,1);
                    plot(clnInd,numcln);
                    hold on;
                    plot(outlier,numcln(value),'xk','linewidth',2);
                    hold off;
                    xlabel('Time Index');
                    ylabel('VW sensor data');
                    title(title_string);                    
                    subplot(2,1,2);
                    plot(clnInd_t,numcln_t);
                    hold on;
                    t_outlier = find(outlier <= length(numcln_t));
                    h=plot(outlier(t_outlier),numcln_t(outlier(t_outlier)),'xk','linewidth',2);
                    hold off;
                    xlabel('Time Index');
                    ylabel('XV sensor data');
                    title(title_string);
                    plot_filename_string = sprintf('Cell%dYear%sSensor%s.fig',num(1,1),fname(1:4),char(sensor_title(col)));                    
                    saveas(h,plot_filename_string)
                    pause(0.01);
                else
                    plot(clnInd,numcln);
                    hold on;
                    plot(outlier,numcln(value),'xk','linewidth',2);
                    hold off;
                    xlabel('Time Index');
                    if(sensor_type == HC)
                        y_label = 'HC Sensor output';
                    elseif(sensor_type == TC)
                        y_label = 'TC Sensor output';
                    elseif(sensor_type == XV)
                        y_label = 'XV Sensor output';
                    end
                    ylabel(y_label);
                    title_string = sprintf('Cell no: %d Year: %s Sensor: %s',num(1,1),fname(1:4),char(sensor_title(col)));
                    title(title_string);
                    pause(0.01);
                end
 
            else
                disp('No outliers found');
            end
        else
            disp('All points out of range');
            disp('No outliers found');
        end
        % Writing the output to the file
        % output - cell variable - can be written as a comma separated
        % value to the output file using cellwrite
        
        % The first output value is the cell number
        output(1:end,1) = num2cell(num(1:end,1));
        % The second output value is the Date
        output(1:end,2) = txt(2:end,2);
        % The third output value is the Hour
        output(1:end,3) = num2cell(num(1:end,3));
        % The fourth output value is the Minute
        output(1:end,4) = num2cell(num(1:end,5));
        % The fifth output value is the sensor id
        output(1:end,5) = {char(sensor_title(col))};
        % The sixth output value is the sensor output
        output(1:end,6) = num2cell(num(1:end,col+5));
        % The seventh output value is the 'is_out_of_range' flag. Any data
        % with value 99999 will be set to 1
        output(out_of_bound,7) = {1};
        if(~isempty(clnInd) && index ~=1)
            normal = setdiff(clnInd,outlier);
            outlier_list = unique(sort(outlier));
            % The eighth output value is the 'is_normal' flag. Any data point
            % which is not an outlier is marked 1
            output(normal,8) = {1};
            % The ninth output value is the 'is_outlier' flag. Any data
            % point which is an outlier is marked 1
            output(outlier_list,9) = {1};
            disp('Outlier count');
            disp(length(outlier_list));
        end
        % The output is written to the file using cellwrite
        result = [result; output];
        cellwrite(out_filename,output_dir,result);
        cd(curpath);
        clear result;
        clear output;
        clear out_of_bound;
        clear normal;
        clear outlier_list;
        clear outlier;
        clear value;
        clear clnInd;
        clear numcln;
        disp(sprintf('Processing sensor %s complete',char(sensor_title(col))));
    end
    disp(sprintf('Processing file %s complete',fname));
end
disp('All files complete');
% Display the time taken for the completion of the program
toc;

