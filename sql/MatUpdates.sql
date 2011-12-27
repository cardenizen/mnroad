select distinct trim('MAT_BINDER_ABCD_TEST          ') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_BINDER_ABCD_TEST           group by test_date   union all
select distinct trim('MAT_BINDER_BBR_TEST           ') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_BINDER_BBR_TEST            group by test_date   union all
select distinct trim('MAT_BINDER_CRITICAL_CRACK_TEMP') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_BINDER_CRITICAL_CRACK_TEMP group by test_date   union all
select distinct trim('MAT_BINDER_DENT_FRACTURE      ') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_BINDER_DENT_FRACTURE       group by test_date   union all
select distinct trim('MAT_BINDER_DILATOMETR_TST     ') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_BINDER_DILATOMETR_TST      group by test_date   union all
select distinct trim('MAT_BINDER_DSR_TESTS          ') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_BINDER_DSR_TESTS           group by test_date   union all
select distinct trim('MAT_BINDER_DT_TEST            ') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_BINDER_DT_TEST             group by test_date   union all
select distinct trim('MAT_BINDER_FATIGUE            ') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_BINDER_FATIGUE             group by test_date   union all
select distinct trim('MAT_BINDER_REPEATED_CREEP     ') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_BINDER_REPEATED_CREEP      group by test_date   union all
select distinct trim('MAT_BINDER_STRAIN_SWEEPS      ') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_BINDER_STRAIN_SWEEPS       group by test_date   union all
select distinct trim('MAT_BINDER_TRAD_TESTS         ') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_BINDER_TRAD_TESTS          group by test_date   union all
select distinct trim('MAT_BINDER_TRADITIONAL_TEST   ') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_BINDER_TRADITIONAL_TEST    group by test_date   union all
select distinct trim('MAT_BINDER_ZERO_SHEAR_VIS     ') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_BINDER_ZERO_SHEAR_VIS      group by test_date   union all
select distinct trim('MAT_CONC_AIR_VOID_RESULTS     ') table_name, to_char(date_tested,'yyyy-mm-dd') test_date, count(*) from MAT_CONC_AIR_VOID_RESULTS      group by date_tested union all
select distinct trim('MAT_CONC_FIELD_RESULTS        ') table_name, to_char(date_tested,'yyyy-mm-dd') test_date, count(*) from MAT_CONC_FIELD_RESULTS         group by date_tested union all
select distinct trim('MAT_CONC_FLEX_STRENGTH        ') table_name, to_char(date_tested,'yyyy-mm-dd') test_date, count(*) from MAT_CONC_FLEX_STRENGTH         group by date_tested union all
select distinct trim('MAT_CONC_FREEZE_THAW_RESULTS  ') table_name, to_char(date_tested,'yyyy-mm-dd') test_date, count(*) from MAT_CONC_FREEZE_THAW_RESULTS   group by date_tested union all
select distinct trim('MAT_CONC_MOD_POISSON_RESULTS  ') table_name, to_char(date_tested,'yyyy-mm-dd') test_date, count(*) from MAT_CONC_MOD_POISSON_RESULTS   group by date_tested union all
select distinct trim('MAT_CONC_RAPID_CHLORIDE       ') table_name, to_char(date_tested,'yyyy-mm-dd') test_date, count(*) from MAT_CONC_RAPID_CHLORIDE        group by date_tested union all
select distinct trim('MAT_CONC_STRENGTH_RESULTS     ') table_name, to_char(date_tested,'yyyy-mm-dd') test_date, count(*) from MAT_CONC_STRENGTH_RESULTS      group by date_tested union all
select distinct trim('MAT_CONC_THERMAL_EXPANSION    ') table_name, to_char(date_cast,'yyyy-mm-dd')   test_date, count(*) from MAT_CONC_THERMAL_EXPANSION     group by date_cast   union all
select distinct trim('MAT_HMA_AGING                 ') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_HMA_AGING                  group by test_date   union all
select distinct trim('MAT_HMA_APA                   ') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_HMA_APA                    group by test_date   union all
select distinct trim('MAT_HMA_BBR_TEST              ') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_HMA_BBR_TEST               group by test_date   union all
select distinct trim('MAT_HMA_COMPLEX_SHEAR_MODU    ') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_HMA_COMPLEX_SHEAR_MODU     group by test_date   union all
select distinct trim('MAT_HMA_CORE_TESTS            ') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_HMA_CORE_TESTS             group by test_date   union all
select distinct trim('MAT_HMA_DCT_TEST              ') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_HMA_DCT_TEST               group by test_date   union all
select distinct trim('MAT_HMA_DILATOMETRIC_TEST     ') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_HMA_DILATOMETRIC_TEST      group by test_date   union all
select distinct trim('MAT_HMA_DYNAMIC_MODULUS       ') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_HMA_DYNAMIC_MODULUS        group by test_date   union all
select distinct trim('MAT_HMA_FLOW_NUMBER           ') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_HMA_FLOW_NUMBER            group by test_date   union all
select distinct trim('MAT_HMA_HAMBURG               ') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_HMA_HAMBURG                group by test_date   union all
select distinct trim('MAT_HMA_IDT_TEST              ') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_HMA_IDT_TEST               group by test_date   union all
select distinct trim('MAT_HMA_IDT_TEST_BACKUP       ') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_HMA_IDT_TEST_BACKUP        group by test_date   union all
select distinct trim('MAT_HMA_INDIRECT_TENS_FATI    ') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_HMA_INDIRECT_TENS_FATI     group by test_date   union all
select distinct trim('MAT_HMA_MIX_TESTS             ') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_HMA_MIX_TESTS              group by test_date   union all
select distinct trim('MAT_HMA_REPEAT_PERM_DEFORM    ') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_HMA_REPEAT_PERM_DEFORM     group by test_date   union all
select distinct trim('MAT_HMA_REPEAT_SHEAR          ') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_HMA_REPEAT_SHEAR           group by test_date   union all
select distinct trim('MAT_HMA_SCB_TEST              ') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_HMA_SCB_TEST               group by test_date   union all
select distinct trim('MAT_HMA_SENB_TEST             ') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_HMA_SENB_TEST              group by test_date   union all
select distinct trim('MAT_HMA_SHEAR_STRENGTH_PAR    ') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_HMA_SHEAR_STRENGTH_PAR     group by test_date   union all
select distinct trim('MAT_HMA_SIEVE_DATA            ') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_HMA_SIEVE_DATA             group by test_date   union all
select distinct trim('MAT_HMA_TRIAXIAL_STATIC_CR    ') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_HMA_TRIAXIAL_STATIC_CR     group by test_date   union all
select distinct trim('MAT_HMA_TRIAXIAL_STRENGTH     ') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_HMA_TRIAXIAL_STRENGTH      group by test_date   union all
select distinct trim('MAT_HMA_TSRST_TEST            ') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_HMA_TSRST_TEST             group by test_date   union all
select distinct trim('MAT_HMA_TTI_OVERLAY           ') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_HMA_TTI_OVERLAY            group by test_date   union all
select distinct trim('MAT_HMA_TTI_OVERLAY_OLD       ') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_HMA_TTI_OVERLAY_OLD        group by test_date   union all
select distinct trim('MAT_HMA_ULTRASONIC_MODULUS    ') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_HMA_ULTRASONIC_MODULUS     group by test_date   union all
select distinct trim('MAT_PCC_COEF_THERM_EXP        ') table_name, to_char(date_tested,'yyyy-mm-dd') test_date, count(*) from MAT_PCC_COEF_THERM_EXP         group by date_tested union all
select distinct trim('MAT_SOIL_TESTS                ') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_SOIL_TESTS                 group by test_date   union all
select distinct trim('MAT_UNBOUND_GRADATIONS        ') table_name, to_char(test_date,'yyyy-mm-dd')   test_date, count(*) from MAT_UNBOUND_GRADATIONS         group by test_date   union all
select distinct trim('MAT_UNBOUND_TUBE_SUCTION      ') table_name, to_char(day,'yyyy-mm-dd')         test_date, count(*) from MAT_UNBOUND_TUBE_SUCTION       group by day;


No date:

MNROAD_ID only
select distinct trim('MAT_CORE_LENGTHS              ') table_name, to_char(test_date,'yyyy-mm-dd') test_date, count(*) from MAT_CORE_LENGTHS                group by test_date union all
select distinct trim('MAT_CONCRETE_MIX_GRAD_RESULTS ') table_name, to_char(test_date,'yyyy-mm-dd') test_date, count(*) from MAT_CONCRETE_MIX_GRAD_RESULTS   group by test_date union all
select distinct trim('MAT_CONCRETE_MIX_DESIGNS      ') table_name, to_char(test_date,'yyyy-mm-dd') test_date, count(*) from MAT_CONCRETE_MIX_DESIGNS        group by test_date union all
select distinct trim('MAT_SOIL_GRAD_RESULTS         ') table_name, to_char(test_date,'yyyy-mm-dd') test_date, count(*) from MAT_SOIL_GRAD_RESULTS           group by test_date union all
select distinct trim('MAT_SOIL_MR_RESULTS           ') table_name, to_char(test_date,'yyyy-mm-dd') test_date, count(*) from MAT_SOIL_MR_RESULTS             group by test_date union all

MNROAD_ID and CELL
select distinct trim('MAT_HMA_ORIGINAL_DENSITY_AIR  ') table_name, to_char(test_date,'yyyy-mm-dd') test_date, count(*) from MAT_HMA_ORIGINAL_DENSITY_AIR    group by test_date union all

XLS_FILE_NAME
select distinct trim('MAT_SOIL_MR_PEAKS             ') table_name, to_char(test_date,'yyyy-mm-dd') test_date, count(*) from MAT_SOIL_MR_PEAKS               group by test_date union all
select distinct trim('MAT_SOIL_MR_PEAKS_UMN         ') table_name, to_char(test_date,'yyyy-mm-dd') test_date, count(*) from MAT_SOIL_MR_PEAKS_UMN           group by test_date union all
select distinct trim('MAT_SOIL_SHEAR_INITSTROKE     ') table_name, to_char(test_date,'yyyy-mm-dd') test_date, count(*) from MAT_SOIL_SHEAR_INITSTROKE       group by test_date union all
select distinct trim('MAT_SOIL_SHEAR_STAGE          ') table_name, to_char(test_date,'yyyy-mm-dd') test_date, count(*) from MAT_SOIL_SHEAR_STAGE            group by test_date union all
select distinct trim('MAT_SOIL_SHEAR_TEST           ') table_name, to_char(test_date,'yyyy-mm-dd') test_date, count(*) from MAT_SOIL_SHEAR_TEST             group by test_date union all
select distinct trim('MAT_SOIL_SHEAR_TEST_UMN       ') table_name, to_char(test_date,'yyyy-mm-dd') test_date, count(*) from MAT_SOIL_SHEAR_TEST_UMN         group by test_date union all

OTHER
select distinct trim('MAT_PROCTOR_CURVES            ') table_name, to_char(test_date,'yyyy-mm-dd') test_date, count(*) from MAT_PROCTOR_CURVES              group by test_date union all
select distinct trim('MAT_SOIL_MR_RAW               ') table_name, to_char(test_date,'yyyy-mm-dd') test_date, count(*) from MAT_SOIL_MR_RAW                 group by test_date union all
