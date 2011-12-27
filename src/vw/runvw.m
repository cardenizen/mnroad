function runvw(varargin)
%    Parameter 1: Script folder
%    Parameter 2: VW or HC or XV or TC
%    Parameter 3: all or sensor input file name
%    Parameter 4: all or sensor number
%    Parameter 5: Input files directory
%    Parameter 6: Output files directory
%    Parameter 7: Number of standard deviations (default is 3)
%    
disp('Start runvw.m')
% If the number of arguments is not 6 or 7 then return
if nargin ~= 6 && nargin ~= 7
    disp('6 or 7 Parameters needed');
    disp('Parameter 1: Script folder                                         ');
    disp('Parameter 2: VW or HC or XV or TC                                  ');
    disp('Parameter 3: all or sensor input file name                         ');
    disp('Parameter 4: all or sensor number                                  ');
    disp('Parameter 5: Input files directory                                 ');
    disp('Parameter 6: Output files directory                                ');
    disp('Parameter 7: Number of standard deviations (optional, default is 3)');
    disp('Please try again with correct number of parameters');
    return;
end
curpath = cd;
cd(char(cellstr(varargin(1))));
%    
n_dev = 0;
if nargin == 6
    n_dev = 3;
else
    n_dev = varargin{7};
end
umn_data_analysis(char(cellstr(varargin(2))),char(cellstr(varargin(3))),char(cellstr(varargin(4))),char(cellstr(varargin(5))),char(cellstr(varargin(6))),n_dev)
disp('End runvw.m')
cd(curpath)
pause(2)
