databaseChangeLog = {

	changeSet(author: "den (generated)", id: "1321036066529-1") {
		createTable(tableName: "APP_CONFIG") {
			column(name: "ID", type: "NUMBER(19,0)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "SYS_C004267", primaryKeyTablespace: "USERS")
			}

			column(name: "VERSION", type: "NUMBER(19,0)") {
				constraints(nullable: "false")
			}

			column(name: "NAME", type: "VARCHAR2(80 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "PARAMETER", type: "VARCHAR2(25 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "STATUS", type: "VARCHAR2(10 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "VAL", type: "VARCHAR2(4000 BYTE)")

			column(name: "CREATED_BY", type: "VARCHAR2(11 BYTE)")

			column(name: "LAST_UPDATED_BY", type: "VARCHAR2(11 BYTE)")

			column(name: "DATE_CREATED", type: "TIMESTAMP(6)") {
				constraints(nullable: "false")
			}

			column(name: "LAST_UPDATED", type: "TIMESTAMP(6)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-2") {
		createTable(tableName: "CELL") {
			column(name: "ID", type: "NUMBER(19,0)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "SYS_C004280", primaryKeyTablespace: "USERS")
			}

			column(name: "VERSION", type: "NUMBER(19,0)") {
				constraints(nullable: "false")
			}

			column(name: "CLASS", type: "VARCHAR2(255 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "CELL_NUMBER", type: "NUMBER(10,0)") {
				constraints(nullable: "false")
			}

			column(name: "CONSTRUCTION_ENDED_DATE", type: "DATE") {
				constraints(nullable: "false")
			}

			column(name: "DEMOLISHED_DATE", type: "DATE")

			column(name: "DESIGN_LIFE", type: "NUMBER(10,0)")

			column(name: "DRAINAGE_SYSTEM", type: "VARCHAR2(255 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "CELL_END_STATION", type: "NUMBER(16,8)")

			column(name: "END_STATION", type: "NUMBER(16,8)") {
				constraints(nullable: "false")
			}

			column(name: "NAME", type: "VARCHAR2(255 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "ROAD_SECTION_ID", type: "NUMBER(19,0)") {
				constraints(nullable: "false")
			}

			column(name: "CELL_START_STATION", type: "NUMBER(16,8)")

			column(name: "START_STATION", type: "NUMBER(16,8)") {
				constraints(nullable: "false")
			}

			column(name: "HMA_DESIGN", type: "VARCHAR2(255 BYTE)")

			column(name: "FIBER_TYPE", type: "VARCHAR2(255 BYTE)")

			column(name: "SURFACE_TEXTURE", type: "VARCHAR2(255 BYTE)")

			column(name: "DATE_CREATED", type: "TIMESTAMP(6)") {
				constraints(nullable: "false")
			}

			column(name: "LAST_UPDATED", type: "TIMESTAMP(6)") {
				constraints(nullable: "false")
			}

			column(name: "SHOULDER_TYPE", type: "VARCHAR2(15 BYTE)")

			column(name: "MNDOT_MIX_SPECIFICATION", type: "VARCHAR2(255 BYTE)")

			column(name: "TIEBARS", type: "NUMBER(1,0)")

			column(name: "LONGITUDINAL_JOINT_SEAL", type: "VARCHAR2(30 BYTE)")

			column(name: "CREATED_BY", type: "VARCHAR2(11 BYTE)")

			column(name: "LAST_UPDATED_BY", type: "VARCHAR2(11 BYTE)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-3") {
		createTable(tableName: "CELL_CELL") {
			column(name: "CELL_COVERED_BY_ID", type: "NUMBER(19,0)")

			column(name: "CELL_ID", type: "NUMBER(19,0)")

			column(name: "CELL_COVERS_ID", type: "NUMBER(19,0)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-4") {
		createTable(tableName: "DISTRESS") {
			column(name: "ID", type: "NUMBER") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "DISTRESSPK", primaryKeyTablespace: "USERS")
			}

			column(name: "VERSION", type: "NUMBER") {
				constraints(nullable: "false")
			}

			column(name: "CREATED_BY", type: "VARCHAR2(255 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "DATE_CREATED", type: "TIMESTAMP(6)") {
				constraints(nullable: "false")
			}

			column(name: "LANE_ID", type: "NUMBER") {
				constraints(nullable: "false")
			}

			column(name: "LAST_UPDATED", type: "TIMESTAMP(6)") {
				constraints(nullable: "false")
			}

			column(name: "LAST_UPDATED_BY", type: "VARCHAR2(255 BYTE)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-5") {
		createTable(tableName: "DISTRESS_AC") {
			column(name: "CELL", type: "NUMBER(30,0)") {
				constraints(nullable: "false")
			}

			column(name: "LANE", type: "VARCHAR2(15 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "DAY", type: "DATE") {
				constraints(nullable: "false")
			}

			column(name: "SURVEYOR1", type: "VARCHAR2(36 BYTE)")

			column(name: "FATIGUE_A_L", type: "NUMBER(5,1)")

			column(name: "FATIGUE_A_M", type: "NUMBER(5,1)")

			column(name: "FATIGUE_A_H", type: "NUMBER(5,1)")

			column(name: "BLOCK_A_L", type: "NUMBER(5,1)")

			column(name: "BLOCK_A_M", type: "NUMBER(5,1)")

			column(name: "BLOCK_A_H", type: "NUMBER(5,1)")

			column(name: "EDGE_L_L", type: "NUMBER(4,1)")

			column(name: "EDGE_L_M", type: "NUMBER(4,1)")

			column(name: "EDGE_L_H", type: "NUMBER(4,1)")

			column(name: "LONG_WP_L_L", type: "NUMBER(5,1)")

			column(name: "LONG_WP_L_M", type: "NUMBER(4,1)")

			column(name: "LONG_WP_L_H", type: "NUMBER(4,1)")

			column(name: "LONG_WP_SEAL_L_L", type: "NUMBER(4,1)")

			column(name: "LONG_WP_SEAL_L_M", type: "NUMBER(4,1)")

			column(name: "LONG_WP_SEAL_L_H", type: "NUMBER(4,1)")

			column(name: "LONG_NWP_L_L", type: "NUMBER(5,1)")

			column(name: "LONG_NWP_L_M", type: "NUMBER(4,1)")

			column(name: "LONG_NWP_L_H", type: "NUMBER(4,1)")

			column(name: "LONG_NWP_SEAL_L_L", type: "NUMBER(4,1)")

			column(name: "LONG_NWP_SEAL_L_M", type: "NUMBER(4,1)")

			column(name: "LONG_NWP_SEAL_L_H", type: "NUMBER(4,1)")

			column(name: "TRANSVERSE_NO_L", type: "NUMBER")

			column(name: "TRANSVERSE_L_L", type: "NUMBER")

			column(name: "TRANSVERSE_NO_M", type: "NUMBER")

			column(name: "TRANSVERSE_L_M", type: "NUMBER")

			column(name: "TRANSVERSE_NO_H", type: "NUMBER")

			column(name: "TRANSVERSE_L_H", type: "NUMBER")

			column(name: "TRANSVERSE_SEAL_NO_L", type: "NUMBER")

			column(name: "TRANSVERSE_SEAL_L_L", type: "NUMBER")

			column(name: "TRANSVERSE_SEAL_NO_M", type: "NUMBER")

			column(name: "TRANSVERSE_SEAL_L_M", type: "NUMBER")

			column(name: "TRANSVERSE_SEAL_NO_H", type: "NUMBER")

			column(name: "TRANSVERSE_SEAL_L_H", type: "NUMBER")

			column(name: "PATCH_NO_L", type: "NUMBER(3,0)")

			column(name: "PATCH_A_L", type: "NUMBER(6,1)")

			column(name: "PATCH_NO_M", type: "NUMBER(3,0)")

			column(name: "PATCH_A_M", type: "NUMBER(4,1)")

			column(name: "PATCH_NO_H", type: "NUMBER(3,0)")

			column(name: "PATCH_A_H", type: "NUMBER(4,1)")

			column(name: "POTHOLES_NO_L", type: "NUMBER(3,0)")

			column(name: "POTHOLES_A_L", type: "NUMBER(4,1)")

			column(name: "POTHOLES_NO_M", type: "NUMBER(3,0)")

			column(name: "POTHOLES_A_M", type: "NUMBER(4,1)")

			column(name: "POTHOLES_NO_H", type: "NUMBER(3,0)")

			column(name: "POTHOLES_A_H", type: "NUMBER(4,1)")

			column(name: "SHOVING_NO", type: "NUMBER(3,0)")

			column(name: "SHOVING_A", type: "NUMBER(4,1)")

			column(name: "BLEEDING_A_L", type: "NUMBER(4,1)")

			column(name: "BLEEDING_A_M", type: "NUMBER(4,1)")

			column(name: "BLEEDING_A_H", type: "NUMBER(4,1)")

			column(name: "POLISH_AGG_A", type: "NUMBER(4,1)")

			column(name: "RAVELING_A_L", type: "NUMBER(5,1)")

			column(name: "RAVELING_A_M", type: "NUMBER(5,1)")

			column(name: "RAVELING_A_H", type: "NUMBER(5,1)")

			column(name: "PUMPING_NO", type: "NUMBER(3,0)")

			column(name: "PUMPING_L", type: "NUMBER(4,1)")

			column(name: "CONST_SHLD_JNT_SEAL_L", type: "NUMBER(3,0)")

			column(name: "CONST_SHLD_JNT_SEAL_M", type: "NUMBER(3,0)")

			column(name: "CONST_SHLD_JNT_SEAL_H", type: "NUMBER(3,0)")

			column(name: "CONST_SHLD_JNT_L", type: "NUMBER(3,0)")

			column(name: "CONST_SHLD_JNT_M", type: "NUMBER(3,0)")

			column(name: "CONST_SHLD_JNT_H", type: "NUMBER(3,0)")

			column(name: "CONST_CL_JNT_SEAL_L", type: "NUMBER(3,0)")

			column(name: "CONST_CL_JNT_SEAL_M", type: "NUMBER(3,0)")

			column(name: "CONST_CL_JNT_SEAL_H", type: "NUMBER(3,0)")

			column(name: "CONST_CL_JNT_L", type: "NUMBER(3,0)")

			column(name: "CONST_CL_JNT_M", type: "NUMBER(3,0)")

			column(name: "CONST_CL_JNT_H", type: "NUMBER(3,0)")

			column(name: "COMMENTS", type: "VARCHAR2(200 BYTE)")

			column(name: "DATE_UPDATED", type: "DATE")

			column(name: "TOPDOWN_L_L", type: "NUMBER(5,1)")

			column(name: "TOPDOWN_L_M", type: "NUMBER(5,1)")

			column(name: "TOPDOWN_L_H", type: "NUMBER(5,1)")

			column(name: "ID", type: "NUMBER(22,0)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-6") {
		createTable(tableName: "DISTRESS_AGG_SURVEY_SEMI") {
			column(name: "DAY", type: "DATE") {
				constraints(nullable: "false")
			}

			column(name: "CELL", type: "NUMBER(2,0)") {
				constraints(nullable: "false")
			}

			column(name: "LANE", type: "VARCHAR2(20 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "RUT_START", type: "NUMBER(3,1)")

			column(name: "RUT_END", type: "NUMBER(3,1)")

			column(name: "WASHBOARD_START", type: "NUMBER(3,1)")

			column(name: "WASHBOARD_END", type: "NUMBER(3,1)")

			column(name: "DUST_START", type: "NUMBER(3,1)")

			column(name: "DUST_END", type: "NUMBER(3,1)")

			column(name: "DISTRESS_COMPARISON", type: "NUMBER(2,0)")

			column(name: "DUST_COMPARISON", type: "NUMBER(2,0)")

			column(name: "AVG_SPEED_MPH", type: "NUMBER(2,0)")

			column(name: "SURVEY_COMMENT", type: "VARCHAR2(225 BYTE)")

			column(name: "ID", type: "NUMBER(19,0)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-7") {
		createTable(tableName: "DISTRESS_ALPS_RESULTS_RUT") {
			column(name: "CELL", type: "NUMBER(3,0)")

			column(name: "STATION", type: "NUMBER(8,1)")

			column(name: "LANE", type: "VARCHAR2(15 BYTE)")

			column(name: "MEASUREMENT_TYPE", type: "VARCHAR2(30 BYTE)")

			column(name: "DAY", type: "DATE")

			column(name: "HOUR_MIN_SEC", type: "VARCHAR2(10 BYTE)")

			column(name: "WHEELPATH", type: "VARCHAR2(10 BYTE)")

			column(name: "RUT_IN", type: "NUMBER(6,4)")

			column(name: "WATER_IN", type: "NUMBER(6,4)")

			column(name: "RUT_VOL_CFPF", type: "NUMBER(6,4)")

			column(name: "WATER_VOL_CFPF", type: "NUMBER(6,4)")

			column(name: "X_RUT_FT", type: "NUMBER(6,4)")

			column(name: "X_WATER_FT", type: "NUMBER(6,4)")

			column(name: "DATE_UPDATED", type: "DATE")

			column(name: "COMMENTS", type: "VARCHAR2(60 BYTE)")

			column(name: "ID", type: "NUMBER(19,0)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-8") {
		createTable(tableName: "DISTRESS_CIRCULR_TEXTR_METER") {
			column(name: "CELL", type: "NUMBER(3,0)") {
				constraints(nullable: "false")
			}

			column(name: "LANE", type: "VARCHAR2(30 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "WHEEL_PATH", type: "VARCHAR2(3 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "DAY", type: "DATE") {
				constraints(nullable: "false")
			}

			column(name: "TIME", type: "VARCHAR2(20 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "OPERATOR", type: "VARCHAR2(30 BYTE)")

			column(name: "FIELD_ID", type: "VARCHAR2(12 BYTE)")

			column(name: "STATION", type: "NUMBER(10,2)")

			column(name: "OFFSET_FT", type: "NUMBER(3,0)")

			column(name: "TRIAL", type: "VARCHAR2(12 BYTE)")

			column(name: "MEAN_PROFILE_DEPTH_MM", type: "NUMBER(4,2)")

			column(name: "ROOT_MEAN_SQUARED_TD_MM", type: "NUMBER(4,2)")

			column(name: "ID", type: "NUMBER(19,0)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-9") {
		createTable(tableName: "DISTRESS_CUPPING") {
			column(name: "CELL", type: "NUMBER(2,0)")

			column(name: "DAY", type: "DATE")

			column(name: "STATION", type: "NUMBER(9,2)")

			column(name: "WHEEL_PATH", type: "VARCHAR2(40 BYTE)")

			column(name: "CRACK_NO", type: "VARCHAR2(10 BYTE)")

			column(name: "CUPPING_X_DISTANCE_INCH", type: "NUMBER(5,2)")

			column(name: "CUPDEPTH_Y_INCH", type: "NUMBER(6,3)")

			column(name: "COMMENTS", type: "VARCHAR2(30 BYTE)")

			column(name: "WEATHER", type: "VARCHAR2(30 BYTE)")

			column(name: "LANE", type: "VARCHAR2(30 BYTE)")

			column(name: "ID", type: "NUMBER(19,0)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-10") {
		createTable(tableName: "DISTRESS_FRICTION_DFT") {
			column(name: "CELL", type: "NUMBER(3,0)")

			column(name: "LANE", type: "VARCHAR2(30 BYTE)")

			column(name: "DAY", type: "DATE")

			column(name: "STATION", type: "NUMBER(10,1)")

			column(name: "OFFSET_FT", type: "NUMBER(10,1)")

			column(name: "WHEELPATH", type: "VARCHAR2(6 BYTE)")

			column(name: "MEASURED_BY", type: "VARCHAR2(20 BYTE)")

			column(name: "RUN_NO", type: "NUMBER(2,0)")

			column(name: "SPEED_KPH", type: "NUMBER(6,1)")

			column(name: "COEFF_FRICTION", type: "NUMBER(10,3)")

			column(name: "COMMENTS", type: "VARCHAR2(100 BYTE)")

			column(name: "DATE_UPDATED", type: "DATE")

			column(name: "ID", type: "NUMBER(19,0)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-11") {
		createTable(tableName: "DISTRESS_FRICTION_TRAILER") {
			column(name: "CELL", type: "NUMBER(3,0)")

			column(name: "LANE", type: "VARCHAR2(15 BYTE)")

			column(name: "DAY", type: "DATE")

			column(name: "TIME", type: "VARCHAR2(5 BYTE)")

			column(name: "FRICTION_NUMBER", type: "NUMBER(3,1)")

			column(name: "PEAK", type: "NUMBER(5,2)")

			column(name: "SPEED_MPH", type: "NUMBER(3,1)")

			column(name: "AIR_TEMP_F", type: "NUMBER(5,2)")

			column(name: "PVMT_TEMP_F", type: "NUMBER(5,2)")

			column(name: "TIRE_TYPE", type: "VARCHAR2(10 BYTE)")

			column(name: "EQUIPMENT", type: "VARCHAR2(50 BYTE)")

			column(name: "DATE_UPDATED", type: "DATE")

			column(name: "LATITUDE", type: "VARCHAR2(14 BYTE)")

			column(name: "LONGITUDE", type: "VARCHAR2(14 BYTE)")

			column(name: "MINFN", type: "NUMBER(2,0)")

			column(name: "MAXFN", type: "NUMBER(2,0)")

			column(name: "SLIP_PERCENT", type: "NUMBER(2,0)")

			column(name: "COMMENTS", type: "VARCHAR2(80 BYTE)")

			column(name: "ID", type: "NUMBER(19,0)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-12") {
		createTable(tableName: "DISTRESS_JPCC") {
			column(name: "CELL", type: "NUMBER(3,0)") {
				constraints(nullable: "false")
			}

			column(name: "LANE", type: "VARCHAR2(20 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "DAY", type: "DATE") {
				constraints(nullable: "false")
			}

			column(name: "SURVEYOR1", type: "VARCHAR2(24 BYTE)")

			column(name: "SURVEYOR2", type: "VARCHAR2(24 BYTE)")

			column(name: "CORNER_BREAKS_NO_L", type: "NUMBER(3,0)")

			column(name: "CORNER_BREAKS_NO_M", type: "NUMBER(3,0)")

			column(name: "CORNER_BREAKS_NO_H", type: "NUMBER(3,0)")

			column(name: "DURAB_CRACK_NO_L", type: "NUMBER(3,0)")

			column(name: "DURAB_CRACK_NO_M", type: "NUMBER(3,0)")

			column(name: "DURAB_CRACK_NO_H", type: "NUMBER(3,0)")

			column(name: "DURAB_CRACK_A_L", type: "NUMBER(4,1)")

			column(name: "DURAB_CRACK_A_M", type: "NUMBER(4,1)")

			column(name: "DURAB_CRACK_A_H", type: "NUMBER(4,1)")

			column(name: "LONG_CRACK_L_L", type: "NUMBER(5,1)")

			column(name: "LONG_CRACK_L_M", type: "NUMBER(5,1)")

			column(name: "LONG_CRACK_L_H", type: "NUMBER(5,1)")

			column(name: "LONG_CRACK_SEAL_L_L", type: "NUMBER(5,1)")

			column(name: "LONG_CRACK_SEAL_L_M", type: "NUMBER(5,1)")

			column(name: "LONG_CRACK_SEAL_L_H", type: "NUMBER(5,1)")

			column(name: "TRANS_CRACK_NO_L", type: "NUMBER(3,0)")

			column(name: "TRANS_CRACK_NO_M", type: "NUMBER(3,0)")

			column(name: "TRANS_CRACK_NO_H", type: "NUMBER(3,0)")

			column(name: "TRANS_CRACK_L_L", type: "NUMBER(5,1)")

			column(name: "TRANS_CRACK_L_M", type: "NUMBER(5,1)")

			column(name: "TRANS_CRACK_L_H", type: "NUMBER(5,1)")

			column(name: "TRANS_CRACK_SEAL_L_L", type: "NUMBER(5,1)")

			column(name: "TRANS_CRACK_SEAL_L_M", type: "NUMBER(5,1)")

			column(name: "TRANS_CRACK_SEAL_L_H", type: "NUMBER(5,1)")

			column(name: "JT_SEALED", type: "VARCHAR2(2 BYTE)")

			column(name: "JOINT_SEAL_TRANS_NO_L", type: "NUMBER(2,0)")

			column(name: "JOINT_SEAL_TRANS_NO_M", type: "NUMBER(2,0)")

			column(name: "JOINT_SEAL_TRANS_NO_H", type: "NUMBER(2,0)")

			column(name: "LONG_JT_SEAL_NO", type: "NUMBER(1,0)")

			column(name: "LONG_JT_SEAL_DAM_L", type: "NUMBER(4,1)")

			column(name: "LONG_SPALLING_L_L", type: "NUMBER(4,1)")

			column(name: "LONG_SPALLING_L_M", type: "NUMBER(4,1)")

			column(name: "LONG_SPALLING_L_H", type: "NUMBER(4,1)")

			column(name: "TRANS_SPALLING_NO_L", type: "NUMBER(2,0)")

			column(name: "TRANS_SPALLING_NO_M", type: "NUMBER(2,0)")

			column(name: "TRANS_SPALLING_NO_H", type: "NUMBER(2,0)")

			column(name: "TRANS_SPALLING_L_L", type: "NUMBER(4,1)")

			column(name: "TRANS_SPALLING_L_M", type: "NUMBER(4,1)")

			column(name: "TRANS_SPALLING_L_H", type: "NUMBER(4,1)")

			column(name: "SCALING_NO", type: "NUMBER(3,0)")

			column(name: "SCALING_A", type: "NUMBER(5,1)")

			column(name: "POLISH_AGG_A", type: "NUMBER(4,1)")

			column(name: "POPOUTS_NO_AREA", type: "NUMBER(4,0)")

			column(name: "BLOWUPS_NO", type: "NUMBER(3,0)")

			column(name: "PATCH_FLEX_NO_L", type: "NUMBER(3,0)")

			column(name: "PATCH_FLEX_NO_M", type: "NUMBER(3,0)")

			column(name: "PATCH_FLEX_NO_H", type: "NUMBER(3,0)")

			column(name: "PATCH_FLEX_A_L", type: "NUMBER(4,1)")

			column(name: "PATCH_FLEX_A_M", type: "NUMBER(4,1)")

			column(name: "PATCH_FLEX_A_H", type: "NUMBER(4,1)")

			column(name: "PATCH_RIGID_NO_L", type: "NUMBER(3,0)")

			column(name: "PATCH_RIGID_NO_M", type: "NUMBER(3,0)")

			column(name: "PATCH_RIGID_NO_H", type: "NUMBER(3,0)")

			column(name: "PATCH_RIGID_A_L", type: "NUMBER(4,1)")

			column(name: "PATCH_RIGID_A_M", type: "NUMBER(4,1)")

			column(name: "PATCH_RIGID_A_H", type: "NUMBER(4,1)")

			column(name: "PUMPING_NO", type: "NUMBER(3,0)")

			column(name: "PUMPING_L", type: "NUMBER(4,1)")

			column(name: "COMMENTS", type: "VARCHAR2(80 BYTE)")

			column(name: "MAP_CRACK_NO", type: "NUMBER(3,0)")

			column(name: "MAP_CRACK_A", type: "NUMBER(5,1)")

			column(name: "LONG_CRACK_NO_L", type: "NUMBER(3,0)")

			column(name: "LONG_CRACK_NO_M", type: "NUMBER(3,0)")

			column(name: "LONG_CRACK_NO_H", type: "NUMBER(3,0)")

			column(name: "DATE_UPDATED", type: "DATE")

			column(name: "ID", type: "NUMBER(19,0)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-13") {
		createTable(tableName: "DISTRESS_LANE_SHOULDER_DROPOFF") {
			column(name: "CELL", type: "NUMBER(3,0)")

			column(name: "LANE", type: "VARCHAR2(60 BYTE)")

			column(name: "DAY", type: "DATE")

			column(name: "TIME", type: "VARCHAR2(20 BYTE)")

			column(name: "OPERATOR", type: "VARCHAR2(30 BYTE)")

			column(name: "STATION", type: "NUMBER(10,1)")

			column(name: "OFFSET_FT", type: "NUMBER(4,1)")

			column(name: "LS_DEPTH_MM", type: "NUMBER(6,1)")

			column(name: "COMMENTS", type: "VARCHAR2(500 BYTE)")

			column(name: "ID", type: "NUMBER(19,0)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-14") {
		createTable(tableName: "DISTRESS_LIGHTWEIGHT_DEFLECT") {
			column(name: "CELL", type: "NUMBER(3,0)") {
				constraints(nullable: "false")
			}

			column(name: "DAY", type: "DATE") {
				constraints(nullable: "false")
			}

			column(name: "STATION", type: "NUMBER(10,1)")

			column(name: "OFFSET_FT", type: "NUMBER(6,1)")

			column(name: "OPERATOR", type: "VARCHAR2(50 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "FIELD_TEST_NUMBER", type: "VARCHAR2(5 BYTE)")

			column(name: "MATERIAL", type: "VARCHAR2(50 BYTE)")

			column(name: "S1_MM", type: "NUMBER(6,3)")

			column(name: "S2_MM", type: "NUMBER(6,3)")

			column(name: "S3_MM", type: "NUMBER(6,3)")

			column(name: "S_MM", type: "NUMBER(6,3)")

			column(name: "EVD_MN_M2", type: "NUMBER(8,3)")

			column(name: "DCP_TEST_NO", type: "VARCHAR2(5 BYTE)")

			column(name: "MOISTURE_TEST_NO", type: "VARCHAR2(5 BYTE)")

			column(name: "NG_DENSITY_TEST_NO", type: "VARCHAR2(5 BYTE)")

			column(name: "COMMENTS", type: "VARCHAR2(50 BYTE)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-15") {
		createTable(tableName: "DISTRESS_NUCLEAR_DENSITY") {
			column(name: "CELL", type: "NUMBER(4,0)") {
				constraints(nullable: "false")
			}

			column(name: "DAY", type: "DATE") {
				constraints(nullable: "false")
			}

			column(name: "STATION", type: "NUMBER(10,1)")

			column(name: "OFFSET_FT", type: "NUMBER(4,1)")

			column(name: "LANE", type: "VARCHAR2(30 BYTE)")

			column(name: "WHEELPATH", type: "VARCHAR2(3 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "OPERATOR", type: "VARCHAR2(30 BYTE)")

			column(name: "EQUIPMENT", type: "VARCHAR2(10 BYTE)")

			column(name: "SETUP", type: "VARCHAR2(30 BYTE)")

			column(name: "MATERIAL_TESTED", type: "VARCHAR2(20 BYTE)")

			column(name: "RUN1_TRANSVERSE_PCF", type: "NUMBER(6,1)")

			column(name: "RUN2_TRANSVERSE_PCF", type: "NUMBER(6,1)")

			column(name: "RUN3_LONGITUDINAL_PCF", type: "NUMBER(6,1)")

			column(name: "RUN4_LONGITUDINAL_PCF", type: "NUMBER(6,1)")

			column(name: "STATION_AVERAGE_PCF", type: "NUMBER(6,1)")

			column(name: "MOISTURE_CONTENT_PCT", type: "NUMBER(6,1)")

			column(name: "COMMENTS", type: "VARCHAR2(100 BYTE)")

			column(name: "ID", type: "NUMBER(19,0)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-16") {
		createTable(tableName: "DISTRESS_OBSI_DATA") {
			column(name: "CELL", type: "NUMBER(3,0)") {
				constraints(nullable: "false")
			}

			column(name: "LANE", type: "VARCHAR2(30 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "DAY", type: "DATE") {
				constraints(nullable: "false")
			}

			column(name: "TIME", type: "VARCHAR2(20 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "OPERATOR", type: "VARCHAR2(30 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "TEST_TIRE", type: "VARCHAR2(10 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "FREQ_HZ", type: "NUMBER(10,0)") {
				constraints(nullable: "false")
			}

			column(name: "LEADING_INTENSITY_LEVEL", type: "NUMBER(10,6)")

			column(name: "LEADING_PI", type: "NUMBER(10,6)")

			column(name: "LEADING_COH", type: "NUMBER(10,6)")

			column(name: "TRAILING_INTENSITY_LEVEL", type: "NUMBER(10,6)")

			column(name: "TRAILING_PI", type: "NUMBER(10,6)")

			column(name: "TRAILING_COH", type: "NUMBER(10,6)")

			column(name: "AVG_INTENSITY_LEVEL", type: "NUMBER(4,1)")

			column(name: "TRIAL", type: "NUMBER(4,0)")

			column(name: "GRIND", type: "VARCHAR2(12 BYTE)")

			column(name: "COMMENTS", type: "VARCHAR2(500 BYTE)")

			column(name: "ID", type: "NUMBER(19,0)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-17") {
		createTable(tableName: "DISTRESS_OBSI_SUMMARY") {
			column(name: "CELL", type: "NUMBER(3,0)") {
				constraints(nullable: "false")
			}

			column(name: "LANE", type: "VARCHAR2(30 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "DAY", type: "DATE") {
				constraints(nullable: "false")
			}

			column(name: "TIME", type: "VARCHAR2(20 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "OPERATOR", type: "VARCHAR2(30 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "TEST_TIRE", type: "VARCHAR2(10 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "LEADING_OBSI", type: "NUMBER(10,6)")

			column(name: "TRAILING_OBSI", type: "NUMBER(10,6)")

			column(name: "TRIAL", type: "NUMBER(4,0)")

			column(name: "GRIND", type: "VARCHAR2(12 BYTE)")

			column(name: "OBSI_AVERAGE", type: "NUMBER(10,6)")

			column(name: "COMMENTS", type: "VARCHAR2(500 BYTE)")

			column(name: "ID", type: "NUMBER(19,0)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-18") {
		createTable(tableName: "DISTRESS_PCC_FAULTS") {
			column(name: "CELL", type: "NUMBER(3,0)") {
				constraints(nullable: "false")
			}

			column(name: "LANE", type: "VARCHAR2(20 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "DAY", type: "DATE") {
				constraints(nullable: "false")
			}

			column(name: "SAMPLE_TIME", type: "NUMBER(4,0)")

			column(name: "JOINT_NUMBER", type: "NUMBER(6,0)") {
				constraints(nullable: "false")
			}

			column(name: "FAULT_DEPTH_MM", type: "NUMBER(6,2)")

			column(name: "DATE_UPDATED", type: "DATE")

			column(name: "OFFSET_FROM_CENTERLINE_FT", type: "NUMBER(10,2)")

			column(name: "ID", type: "NUMBER(19,0)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-19") {
		createTable(tableName: "DISTRESS_PERMEABILITY_DIRECT") {
			column(name: "CELL", type: "NUMBER(3,0)") {
				constraints(nullable: "false")
			}

			column(name: "DAY", type: "DATE") {
				constraints(nullable: "false")
			}

			column(name: "TIME", type: "VARCHAR2(10 BYTE)")

			column(name: "STATION", type: "NUMBER(10,2)")

			column(name: "OFFSET_FT", type: "NUMBER(4,1)")

			column(name: "LANE", type: "VARCHAR2(50 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "PANEL_NO", type: "NUMBER(3,0)") {
				constraints(nullable: "false")
			}

			column(name: "RESISTIVITY_KOHM_CM", type: "NUMBER(3,0)")

			column(name: "PA_MBAR", type: "NUMBER(6,2)")

			column(name: "TMAX_S", type: "NUMBER(3,0)")

			column(name: "DELTA_PMAX_MBAR", type: "NUMBER(3,1)")

			column(name: "KT_10E_16_M2", type: "NUMBER(6,3)")

			column(name: "L_MM", type: "NUMBER(3,1)")

			column(name: "CONCRETE_QUALITY", type: "VARCHAR2(20 BYTE)")

			column(name: "COMMENTS", type: "VARCHAR2(100 BYTE)")

			column(name: "ID", type: "NUMBER(19,0)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-20") {
		createTable(tableName: "DISTRESS_RIDE_LISA") {
			column(name: "CELL", type: "NUMBER(4,0)") {
				constraints(nullable: "false")
			}

			column(name: "DAY", type: "DATE") {
				constraints(nullable: "false")
			}

			column(name: "LANE", type: "VARCHAR2(30 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "WHEELPATH", type: "VARCHAR2(10 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "OPERATOR1", type: "VARCHAR2(50 BYTE)")

			column(name: "OPERATOR2", type: "VARCHAR2(50 BYTE)")

			column(name: "COLLECTION_METHOD", type: "VARCHAR2(30 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "RUN_NO", type: "NUMBER(2,0)") {
				constraints(nullable: "false")
			}

			column(name: "IRI_RUN_M_KM", type: "NUMBER(5,2)")

			column(name: "COMMENTS", type: "VARCHAR2(50 BYTE)")

			column(name: "ROLINE_IRI_M_KM", type: "NUMBER(6,2)")

			column(name: "ID", type: "NUMBER(19,0)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-21") {
		createTable(tableName: "DISTRESS_RIDE_PATHWAYS") {
			column(name: "CELL", type: "NUMBER(4,0)")

			column(name: "LANE", type: "VARCHAR2(16 BYTE)")

			column(name: "TEST_LENGTH_FT", type: "NUMBER(6,2)") {
				constraints(nullable: "false")
			}

			column(name: "DAY", type: "DATE") {
				constraints(nullable: "false")
			}

			column(name: "TIME", type: "NUMBER(4,0)")

			column(name: "RUN", type: "NUMBER(4,0)")

			column(name: "IRI_LWP_M_KM", type: "NUMBER(6,2)")

			column(name: "IRI_RWP_M_KM", type: "NUMBER(6,2)")

			column(name: "IRI_AVG_M_KM", type: "NUMBER(6,2)")

			column(name: "RUT_LWP_IN", type: "NUMBER(6,2)")

			column(name: "RUT_CEN_IN", type: "NUMBER(6,2)")

			column(name: "RUT_RWP_IN", type: "NUMBER(6,2)")

			column(name: "RUT_AVG_IN", type: "NUMBER(6,2)")

			column(name: "PSR", type: "NUMBER(6,2)")

			column(name: "SR", type: "NUMBER(6,2)")

			column(name: "DATE_UPDATED", type: "DATE")

			column(name: "COMMENTS", type: "VARCHAR2(250 BYTE)")

			column(name: "RQI", type: "NUMBER(3,1)")

			column(name: "ID", type: "NUMBER(19,0)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-22") {
		createTable(tableName: "DISTRESS_RIDE_PAVETECH") {
			column(name: "DAY", type: "DATE") {
				constraints(nullable: "false")
			}

			column(name: "CELL", type: "NUMBER(2,0)") {
				constraints(nullable: "false")
			}

			column(name: "LANE", type: "VARCHAR2(20 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "IRI_M_PER_KM", type: "NUMBER(6,2)")

			column(name: "RUT_IN", type: "NUMBER(6,2)")

			column(name: "ID", type: "NUMBER(19,0)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-23") {
		createTable(tableName: "DISTRESS_RUTTING_DIPSTICK") {
			column(name: "DAY", type: "DATE") {
				constraints(nullable: "false")
			}

			column(name: "CELL", type: "NUMBER(2,0)") {
				constraints(nullable: "false")
			}

			column(name: "STATION", type: "NUMBER(8,2)") {
				constraints(nullable: "false")
			}

			column(name: "LANE", type: "VARCHAR2(15 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "LEFT_WP_DEPTH_IN", type: "NUMBER(4,3)")

			column(name: "RIGHT_WP_DEPTH_IN", type: "NUMBER(4,3)")

			column(name: "ID", type: "NUMBER(19,0)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-24") {
		createTable(tableName: "DISTRESS_RUTTING_STRAIGHT_EDGE") {
			column(name: "DAY", type: "DATE") {
				constraints(nullable: "false")
			}

			column(name: "CELL", type: "NUMBER(2,0)") {
				constraints(nullable: "false")
			}

			column(name: "STATION", type: "NUMBER(10,2)") {
				constraints(nullable: "false")
			}

			column(name: "LANE", type: "VARCHAR2(15 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "LEFT_WP_DEPTH_IN", type: "NUMBER(6,5)")

			column(name: "RIGHT_WP_DEPTH_IN", type: "NUMBER(6,5)")

			column(name: "COMMENTS", type: "VARCHAR2(255 BYTE)")

			column(name: "DATE_UPDATED", type: "DATE")

			column(name: "ID", type: "NUMBER(19,0)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-25") {
		createTable(tableName: "DISTRESS_SAND_PATCH") {
			column(name: "CELL", type: "NUMBER(3,0)") {
				constraints(nullable: "false")
			}

			column(name: "LANE", type: "VARCHAR2(30 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "DAY", type: "DATE") {
				constraints(nullable: "false")
			}

			column(name: "TIME", type: "VARCHAR2(12 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "STATION", type: "NUMBER(8,1)")

			column(name: "OFFSET_FT", type: "NUMBER(3,0)") {
				constraints(nullable: "false")
			}

			column(name: "MEASURED_BY", type: "VARCHAR2(80 BYTE)")

			column(name: "METHOD", type: "VARCHAR2(10 BYTE)")

			column(name: "FIELD_ID", type: "VARCHAR2(12 BYTE)")

			column(name: "X1_MM", type: "NUMBER(5,1)")

			column(name: "X2_MM", type: "NUMBER(5,1)")

			column(name: "X3_MM", type: "NUMBER(5,1)")

			column(name: "X4_MM", type: "NUMBER(5,1)")

			column(name: "X_AVG_MM", type: "NUMBER(5,1)")

			column(name: "VOLUME_MM3", type: "NUMBER(6,0)")

			column(name: "TEXTURE_MM", type: "NUMBER(8,3)")

			column(name: "COMMENTS", type: "VARCHAR2(200 BYTE)")

			column(name: "ID", type: "NUMBER(19,0)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-26") {
		createTable(tableName: "DISTRESS_SCHMIDT_HAMMER") {
			column(name: "CELL", type: "NUMBER(3,0)")

			column(name: "DAY", type: "DATE")

			column(name: "STATION", type: "NUMBER(10,1)")

			column(name: "OFFSET_FT", type: "NUMBER(4,1)")

			column(name: "PANEL_NO", type: "NUMBER(3,0)")

			column(name: "LANE", type: "VARCHAR2(60 BYTE)")

			column(name: "REBOUND_NO", type: "NUMBER(4,1)")

			column(name: "COMP_STRENGTH_PSI", type: "NUMBER(6,0)")

			column(name: "STDEV_PSI", type: "NUMBER(4,1)")

			column(name: "COMMENTS", type: "VARCHAR2(150 BYTE)")

			column(name: "ID", type: "NUMBER(19,0)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-27") {
		createTable(tableName: "DISTRESS_SOUND_ABSORPTION") {
			column(name: "CELL", type: "NUMBER(3,0)") {
				constraints(nullable: "false")
			}

			column(name: "STATION", type: "VARCHAR2(20 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "LANE", type: "VARCHAR2(30 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "WHEELPATH", type: "VARCHAR2(3 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "TRIAL", type: "VARCHAR2(12 BYTE)")

			column(name: "OPERATOR", type: "VARCHAR2(30 BYTE)")

			column(name: "DAY", type: "DATE") {
				constraints(nullable: "false")
			}

			column(name: "TIME", type: "VARCHAR2(20 BYTE)")

			column(name: "TEMP_C", type: "NUMBER(5,2)")

			column(name: "ATMOS_PRESSURE_PASCALS", type: "NUMBER(10,2)")

			column(name: "CONDITION", type: "VARCHAR2(20 BYTE)")

			column(name: "FREQUENCY_HZ", type: "NUMBER(6,2)")

			column(name: "SOUND_ABSORPTION", type: "NUMBER(6,2)")

			column(name: "COMMENTS", type: "VARCHAR2(100 BYTE)")

			column(name: "OFFSET_FT", type: "NUMBER(4,1)")

			column(name: "ID", type: "NUMBER(19,0)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-28") {
		createTable(tableName: "DISTRESS_WATER_PERMEABILITY") {
			column(name: "CELL", type: "NUMBER(3,0)") {
				constraints(nullable: "false")
			}

			column(name: "DAY", type: "DATE") {
				constraints(nullable: "false")
			}

			column(name: "TIME", type: "VARCHAR2(10 BYTE)")

			column(name: "STATION", type: "NUMBER(10,2)")

			column(name: "OFFSET_FT", type: "NUMBER(4,1)")

			column(name: "LANE", type: "VARCHAR2(50 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "FLOW_TIME_S", type: "NUMBER(6,2)")

			column(name: "INITIAL_HEAD_CM", type: "NUMBER(6,2)")

			column(name: "FINAL_HEAD_CM", type: "NUMBER(6,2)")

			column(name: "PAVEMENT_THICKNESS_CM", type: "NUMBER(4,1)")

			column(name: "CROSS_SECTION_AREA_CM2", type: "NUMBER(6,2)")

			column(name: "COMMENTS", type: "VARCHAR2(100 BYTE)")

			column(name: "ID", type: "NUMBER(19,0)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-29") {
		createTable(tableName: "FACILITY") {
			column(name: "ID", type: "NUMBER(19,0)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "SYS_C004287", primaryKeyTablespace: "USERS")
			}

			column(name: "VERSION", type: "NUMBER(19,0)") {
				constraints(nullable: "false")
			}

			column(name: "NAME", type: "VARCHAR2(400 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "DESCRIPTION", type: "VARCHAR2(400 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "DATE_CREATED", type: "TIMESTAMP(6)") {
				constraints(nullable: "false")
			}

			column(name: "LAST_UPDATED", type: "TIMESTAMP(6)") {
				constraints(nullable: "false")
			}

			column(name: "CREATED_BY", type: "VARCHAR2(11 BYTE)")

			column(name: "LAST_UPDATED_BY", type: "VARCHAR2(11 BYTE)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-30") {
		createTable(tableName: "JSEC_PERMISSION") {
			column(name: "ID", type: "NUMBER(19,0)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "SYS_C004357", primaryKeyTablespace: "USERS")
			}

			column(name: "VERSION", type: "NUMBER(19,0)") {
				constraints(nullable: "false")
			}

			column(name: "POSSIBLE_ACTIONS", type: "VARCHAR2(255 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "TYPE", type: "VARCHAR2(255 BYTE)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-31") {
		createTable(tableName: "JSEC_ROLE") {
			column(name: "ID", type: "NUMBER(19,0)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "SYS_C004371", primaryKeyTablespace: "USERS")
			}

			column(name: "VERSION", type: "NUMBER(19,0)") {
				constraints(nullable: "false")
			}

			column(name: "NAME", type: "VARCHAR2(255 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "IN_DIRECTORY", type: "NUMBER(1,0)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-32") {
		createTable(tableName: "JSEC_ROLE_PERMISSION_REL") {
			column(name: "ID", type: "NUMBER(19,0)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "SYS_C004346", primaryKeyTablespace: "USERS")
			}

			column(name: "VERSION", type: "NUMBER(19,0)") {
				constraints(nullable: "false")
			}

			column(name: "ACTIONS", type: "VARCHAR2(255 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "PERMISSION_ID", type: "NUMBER(19,0)") {
				constraints(nullable: "false")
			}

			column(name: "ROLE_ID", type: "NUMBER(19,0)") {
				constraints(nullable: "false")
			}

			column(name: "TARGET", type: "VARCHAR2(255 BYTE)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-33") {
		createTable(tableName: "JSEC_USER") {
			column(name: "ID", type: "NUMBER(19,0)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "SYS_C004367", primaryKeyTablespace: "USERS")
			}

			column(name: "VERSION", type: "NUMBER(19,0)") {
				constraints(nullable: "false")
			}

			column(name: "PASSWORD_HASH", type: "VARCHAR2(255 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "USERNAME", type: "VARCHAR2(255 BYTE)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-34") {
		createTable(tableName: "JSEC_USER_PERMISSION_REL") {
			column(name: "ID", type: "NUMBER(19,0)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "SYS_C004352", primaryKeyTablespace: "USERS")
			}

			column(name: "VERSION", type: "NUMBER(19,0)") {
				constraints(nullable: "false")
			}

			column(name: "ACTIONS", type: "VARCHAR2(255 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "PERMISSION_ID", type: "NUMBER(19,0)") {
				constraints(nullable: "false")
			}

			column(name: "TARGET", type: "VARCHAR2(255 BYTE)")

			column(name: "USER_ID", type: "NUMBER(19,0)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-35") {
		createTable(tableName: "JSEC_USER_ROLE_REL") {
			column(name: "ID", type: "NUMBER(19,0)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "SYS_C004362", primaryKeyTablespace: "USERS")
			}

			column(name: "VERSION", type: "NUMBER(19,0)") {
				constraints(nullable: "false")
			}

			column(name: "ROLE_ID", type: "NUMBER(19,0)") {
				constraints(nullable: "false")
			}

			column(name: "USER_ID", type: "NUMBER(19,0)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-36") {
		createTable(tableName: "LANE") {
			column(name: "ID", type: "NUMBER(19,0)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "SYS_C004296", primaryKeyTablespace: "USERS")
			}

			column(name: "VERSION", type: "NUMBER(19,0)") {
				constraints(nullable: "false")
			}

			column(name: "CELL_ID", type: "NUMBER(19,0)") {
				constraints(nullable: "false")
			}

			column(name: "LANE_NUM", type: "NUMBER(10,0)") {
				constraints(nullable: "false")
			}

			column(name: "NAME", type: "VARCHAR2(20 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "WIDTH", type: "NUMBER(10,0)") {
				constraints(nullable: "false")
			}

			column(name: "DATE_CREATED", type: "TIMESTAMP(6)") {
				constraints(nullable: "false")
			}

			column(name: "LAST_UPDATED", type: "TIMESTAMP(6)") {
				constraints(nullable: "false")
			}

			column(name: "PANEL_LENGTH", type: "FLOAT(126)")

			column(name: "PANEL_WIDTH", type: "FLOAT(126)")

			column(name: "CREATED_BY", type: "VARCHAR2(11 BYTE)")

			column(name: "LAST_UPDATED_BY", type: "VARCHAR2(11 BYTE)")

			column(name: "OFFSET_REF", type: "VARCHAR2(1 BYTE)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-37") {
		createTable(tableName: "LAYER") {
			column(name: "ID", type: "NUMBER(19,0)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "SYS_C004305", primaryKeyTablespace: "USERS")
			}

			column(name: "VERSION", type: "NUMBER(19,0)") {
				constraints(nullable: "false")
			}

			column(name: "CONSTRUCT_END_DATE", type: "DATE")

			column(name: "CONSTRUCT_START_DATE", type: "DATE") {
				constraints(nullable: "false")
			}

			column(name: "LANE_ID", type: "NUMBER(19,0)")

			column(name: "LAYER_NUM", type: "NUMBER(2,0)") {
				constraints(nullable: "false")
			}

			column(name: "MATERIAL_ID", type: "NUMBER(19,0)") {
				constraints(nullable: "false")
			}

			column(name: "THICKNESS", type: "NUMBER(8,3)") {
				constraints(nullable: "false")
			}

			column(name: "DATE_CREATED", type: "TIMESTAMP(6)") {
				constraints(nullable: "false")
			}

			column(name: "LAST_UPDATED", type: "TIMESTAMP(6)") {
				constraints(nullable: "false")
			}

			column(name: "DOWEL_BAR_LENGTH", type: "NUMBER(8,2)")

			column(name: "DOWEL_BAR_DIAMETER", type: "NUMBER(8,2)")

			column(name: "TRANSVERSE_STEEL", type: "NUMBER(1,0)")

			column(name: "CREATED_BY", type: "VARCHAR2(11 BYTE)")

			column(name: "LAST_UPDATED_BY", type: "VARCHAR2(11 BYTE)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-38") {
		createTable(tableName: "MAT_BINDER_ABCD_TEST") {
			column(name: "MNROAD_ID", type: "VARCHAR2(10 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "TESTING_LAB", type: "VARCHAR2(50 BYTE)")

			column(name: "TEST_DATE", type: "DATE")

			column(name: "SAMPLE_DESCRIPTION", type: "VARCHAR2(50 BYTE)")

			column(name: "CONDITION", type: "VARCHAR2(50 BYTE)")

			column(name: "CRACK_TEMP_C", type: "NUMBER(6,1)")

			column(name: "STRAIN_JUMP_MICROSTRAIN", type: "NUMBER(6,1)")

			column(name: "COOLING_RATE_DEGC_HR", type: "NUMBER(6,1)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-39") {
		createTable(tableName: "MAT_BINDER_BBR_TEST") {
			column(name: "MNROAD_ID", type: "VARCHAR2(10 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "DESCRIPTION", type: "VARCHAR2(500 BYTE)")

			column(name: "HMA_LIFT", type: "NUMBER(2,0)")

			column(name: "SAMPLE_TYPE", type: "VARCHAR2(50 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "TEST_DATE", type: "DATE")

			column(name: "TESTING_LAB", type: "VARCHAR2(36 BYTE)")

			column(name: "FILE_NAME", type: "VARCHAR2(12 BYTE)")

			column(name: "TARGET_TEMP_C", type: "NUMBER(5,1)")

			column(name: "WIDTH_MM", type: "NUMBER(5,1)")

			column(name: "THICK_MM", type: "NUMBER(5,2)")

			column(name: "TIME_SEC", type: "NUMBER(4,0)")

			column(name: "P_FORCE_MN", type: "NUMBER(4,0)")

			column(name: "DEFLECTION_MM", type: "NUMBER(5,3)")

			column(name: "MEASURED_STIFFNESS_MPA", type: "NUMBER(5,1)")

			column(name: "M_VALUE", type: "VARCHAR2(8 BYTE)")

			column(name: "FTEMP_STIFFNESS", type: "NUMBER(5,1)")

			column(name: "FTEMP_MVALUE", type: "NUMBER(5,1)")

			column(name: "LOW_TEMP_PGGRADE", type: "NUMBER(5,1)")

			column(name: "DATE_UPDATED", type: "DATE")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-40") {
		createTable(tableName: "MAT_BINDER_CRITICAL_CRACK_TEMP") {
			column(name: "MNROAD_ID", type: "VARCHAR2(11 BYTE)")

			column(name: "TESTING_LAB", type: "VARCHAR2(36 BYTE)")

			column(name: "TEST_DATE", type: "DATE")

			column(name: "DESCRIPTION", type: "VARCHAR2(24 BYTE)")

			column(name: "CONDITION", type: "VARCHAR2(12 BYTE)")

			column(name: "S_300_MPA", type: "NUMBER(12,10)")

			column(name: "M_POINT_300", type: "NUMBER(12,10)")

			column(name: "STRAIN_1_PERCENT", type: "NUMBER(12,10)")

			column(name: "PC_24", type: "NUMBER")

			column(name: "PC_18", type: "NUMBER")

			column(name: "DATE_UPDATED", type: "DATE")

			column(name: "BBR_PC_18", type: "NUMBER")

			column(name: "BBR_DENT_PC_1", type: "NUMBER")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-41") {
		createTable(tableName: "MAT_BINDER_DENT_FRACTURE") {
			column(name: "MNROAD_ID", type: "VARCHAR2(11 BYTE)")

			column(name: "TESTING_LAB", type: "VARCHAR2(36 BYTE)")

			column(name: "TEST_DATE", type: "DATE")

			column(name: "DESCRIPTION", type: "VARCHAR2(24 BYTE)")

			column(name: "CONDITION", type: "VARCHAR2(12 BYTE)")

			column(name: "TEMP_C", type: "NUMBER(5,2)")

			column(name: "FRACTURE_TOUGHNESS_KPA_SQRTMTR", type: "NUMBER")

			column(name: "DATE_UPDATED", type: "DATE")

			column(name: "FAIL_STRESS_MPA", type: "NUMBER(8,3)")

			column(name: "FAIL_STRAIN", type: "NUMBER(8,3)")

			column(name: "PEAK_LOAD_KN", type: "NUMBER(8,4)")

			column(name: "STRAIN_RATE_PERCENT_PER_MINUTE", type: "NUMBER")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-42") {
		createTable(tableName: "MAT_BINDER_DILATOMETR_TST") {
			column(name: "MAT_BINDER_DILATOMETR_TST_PK", type: "NUMBER(22,0)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "MAT_BINDER_DILATOMETR_TST_PKC", primaryKeyTablespace: "USERS")
			}

			column(name: "MNROAD_ID", type: "VARCHAR2(11 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "TESTING_LAB", type: "VARCHAR2(36 BYTE)")

			column(name: "TEST_DATE", type: "DATE")

			column(name: "SAMPLE_DESC", type: "VARCHAR2(40 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "SAMPLE_TYPE", type: "VARCHAR2(20 BYTE)")

			column(name: "THERMAL_CYCLE", type: "VARCHAR2(25 BYTE)")

			column(name: "GLASS_TRANS_TEMP_C", type: "NUMBER(4,1)")

			column(name: "COEFF_BELOW_TG", type: "NUMBER(10,8)")

			column(name: "COEFF_ABOVE_TG", type: "NUMBER(10,8)")

			column(name: "CREATE_USER", type: "VARCHAR2(8 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "CREATE_DATE", type: "DATE") {
				constraints(nullable: "false")
			}

			column(name: "MODIFY_USER", type: "VARCHAR2(8 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "MODIFY_DATE", type: "DATE") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-43") {
		createTable(tableName: "MAT_BINDER_DSR_TESTS") {
			column(name: "MNROAD_ID", type: "VARCHAR2(11 BYTE)")

			column(name: "MNROAD_ID_COMMENT", type: "VARCHAR2(60 BYTE)")

			column(name: "HMA_LIFT", type: "NUMBER")

			column(name: "LAB_ID", type: "VARCHAR2(24 BYTE)")

			column(name: "TESTING_LAB", type: "VARCHAR2(24 BYTE)")

			column(name: "TEST_DATE", type: "DATE")

			column(name: "DESCRIPTION", type: "VARCHAR2(200 BYTE)")

			column(name: "SAMPLE_TYPE", type: "VARCHAR2(24 BYTE)")

			column(name: "FREQ_RAD_S", type: "NUMBER")

			column(name: "DELTA_DEGREES", type: "NUMBER")

			column(name: "GPRIME_PA", type: "NUMBER")

			column(name: "GDOUBLEPRIME_PA", type: "NUMBER")

			column(name: "GSTAR_PA", type: "NUMBER")

			column(name: "G_TIMES_SIN_DELTA_KPA", type: "NUMBER")

			column(name: "G_DIV_SIN_DELTA_KPA", type: "NUMBER")

			column(name: "GAP_MICROMETERS", type: "NUMBER")

			column(name: "VISCOSITY_PA_S", type: "NUMBER")

			column(name: "TORQUE_MICRONEWTON_M", type: "NUMBER")

			column(name: "PERCENT_STRAIN", type: "NUMBER")

			column(name: "TEMP_C", type: "NUMBER")

			column(name: "DIAMETER_MM", type: "NUMBER")

			column(name: "OSC_STRESS_PA", type: "NUMBER")

			column(name: "FAIL_TEMP_C", type: "NUMBER")

			column(name: "HIGH_TEMP_PG_GRADE_C", type: "NUMBER")

			column(name: "DATE_UPDATED", type: "DATE")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-44") {
		createTable(tableName: "MAT_BINDER_DT_TEST") {
			column(name: "MNROAD_ID", type: "VARCHAR2(20 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "SAMPLE_TYPE", type: "VARCHAR2(40 BYTE)")

			column(name: "DESCRIPTION", type: "VARCHAR2(200 BYTE)")

			column(name: "TEST_DATE", type: "DATE")

			column(name: "TESTED_BY", type: "VARCHAR2(30 BYTE)")

			column(name: "TARGET_TEMP_C", type: "NUMBER(3,0)")

			column(name: "DIRECT_TENSION_STRAIN", type: "NUMBER(9,6)")

			column(name: "DIRECT_TENSION_STRESS_MPA", type: "NUMBER(5,3)")

			column(name: "STRAIN_RATE_PERCENT_PER_MINUTE", type: "NUMBER")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-45") {
		createTable(tableName: "MAT_BINDER_FATIGUE") {
			column(name: "MNROAD_ID", type: "VARCHAR2(11 BYTE)")

			column(name: "TESTING_LAB", type: "VARCHAR2(36 BYTE)")

			column(name: "TEST_DATE", type: "DATE")

			column(name: "DESCRIPTION", type: "VARCHAR2(24 BYTE)")

			column(name: "CONDITION", type: "VARCHAR2(12 BYTE)")

			column(name: "FREQ_RAD_S", type: "NUMBER")

			column(name: "DELTA_DEGREES", type: "NUMBER")

			column(name: "GPRIME_PA", type: "NUMBER(10,0)")

			column(name: "GDOUBLEPRIME_PA", type: "NUMBER(10,0)")

			column(name: "GSTAR_PA", type: "NUMBER(10,0)")

			column(name: "GAP_MICROMETERS", type: "NUMBER")

			column(name: "VISCOSITY_PA_S", type: "NUMBER(10,0)")

			column(name: "STRESS_PA", type: "NUMBER(10,0)")

			column(name: "TORQUE_MICRON_M", type: "NUMBER")

			column(name: "STRAIN_PERCENT", type: "NUMBER(8,6)")

			column(name: "TEMP_C", type: "NUMBER")

			column(name: "TIME_SEC", type: "NUMBER")

			column(name: "CYCLE_N", type: "NUMBER")

			column(name: "DISSIPATED_ENERGY_WN", type: "NUMBER(12,6)")

			column(name: "DISSIPATED_ENERGY_RATIO_DER", type: "NUMBER(12,6)")

			column(name: "DATE_UPDATED", type: "DATE")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-46") {
		createTable(tableName: "MAT_BINDER_REPEATED_CREEP") {
			column(name: "MNROAD_ID", type: "VARCHAR2(11 BYTE)")

			column(name: "TESTING_LAB", type: "VARCHAR2(36 BYTE)")

			column(name: "TEST_DATE", type: "DATE")

			column(name: "DESCRIPTION", type: "VARCHAR2(24 BYTE)")

			column(name: "CONDITION", type: "VARCHAR2(12 BYTE)")

			column(name: "COMPLIANCE_M2_PER_NEWTON", type: "NUMBER(18,12)")

			column(name: "GAP_MICROMETERS", type: "NUMBER")

			column(name: "TIME_SEC", type: "NUMBER")

			column(name: "SHEAR_STRESS_PA", type: "NUMBER")

			column(name: "STRAIN", type: "NUMBER")

			column(name: "TEMP_C", type: "NUMBER")

			column(name: "TORQUE_MICRON_M", type: "NUMBER")

			column(name: "VISCOSITY_PA_S", type: "NUMBER")

			column(name: "DIAMETER_MM", type: "NUMBER")

			column(name: "DATE_UPDATED", type: "DATE")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-47") {
		createTable(tableName: "MAT_BINDER_STRAIN_SWEEPS") {
			column(name: "MNROAD_ID", type: "VARCHAR2(11 BYTE)")

			column(name: "TESTING_LAB", type: "VARCHAR2(36 BYTE)")

			column(name: "TEST_DATE", type: "DATE")

			column(name: "DESCRIPTION", type: "VARCHAR2(24 BYTE)")

			column(name: "CONDITION", type: "VARCHAR2(12 BYTE)")

			column(name: "FREQ_RAD_S", type: "NUMBER")

			column(name: "DELTA_DEGREES", type: "NUMBER")

			column(name: "GPRIME_PA", type: "NUMBER")

			column(name: "GDOUBLEPRIME_PA", type: "NUMBER")

			column(name: "GSTAR_PA", type: "NUMBER")

			column(name: "G_TIMES_SIN_DELTA_KPA", type: "NUMBER")

			column(name: "G_DIV_SIN_DELTA_KPA", type: "NUMBER")

			column(name: "GAP_MICROMETERS", type: "NUMBER")

			column(name: "VISCOSITY_PA_S", type: "NUMBER")

			column(name: "TORQUE_MICRON_M", type: "NUMBER")

			column(name: "STRAIN_PERCENT", type: "NUMBER")

			column(name: "TEMP_C", type: "NUMBER")

			column(name: "TIME_SEC", type: "NUMBER")

			column(name: "DIAMETER_MM", type: "NUMBER")

			column(name: "DATE_UPDATED", type: "DATE")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-48") {
		createTable(tableName: "MAT_BINDER_TRAD_TESTS") {
			column(name: "MNROAD_ID", type: "VARCHAR2(10 BYTE)")

			column(name: "LAB_NO", type: "VARCHAR2(12 BYTE)")

			column(name: "DESCRIPTION", type: "VARCHAR2(50 BYTE)")

			column(name: "TEST_DATE", type: "DATE")

			column(name: "TESTING_LAB", type: "VARCHAR2(20 BYTE)")

			column(name: "WEIGHT_GALLON_60F", type: "NUMBER(8,3)")

			column(name: "PEN_77F", type: "NUMBER(4,0)")

			column(name: "PEN_40F", type: "NUMBER(4,0)")

			column(name: "PEN_55F", type: "NUMBER(4,0)")

			column(name: "PEN_90F", type: "NUMBER(4,0)")

			column(name: "VISC_140F", type: "NUMBER(5,0)")

			column(name: "VISC_275F", type: "NUMBER(6,1)")

			column(name: "DUCTILITY_77F", type: "NUMBER(4,0)")

			column(name: "SOFTENING_POINT", type: "NUMBER(4,0)")

			column(name: "TFO_TEST_PERCENT_LOSS", type: "NUMBER(6,2)")

			column(name: "TFO_PEN_RES_PERCENT_ORIGINAL", type: "NUMBER(6,2)")

			column(name: "TFO_DUCT_RES_77F", type: "NUMBER(4,0)")

			column(name: "TFO_DUCT_RES_140F", type: "NUMBER(6,0)")

			column(name: "TFO_DUCT_RES_275F", type: "NUMBER(6,0)")

			column(name: "TFO_SOFTENING_POINT", type: "NUMBER(4,0)")

			column(name: "DATE_UPDATED", type: "DATE")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-49") {
		createTable(tableName: "MAT_CONC_AIR_VOID_RESULTS") {
			column(name: "MNROAD_ID", type: "VARCHAR2(11 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "DATE_TESTED", type: "DATE")

			column(name: "PERCENT_AIR", type: "NUMBER(4,2)")

			column(name: "SPECIFIC_SURFACE", type: "NUMBER(4,0)")

			column(name: "SPACING_FACTOR", type: "NUMBER(6,4)")

			column(name: "VOIDS_PER_INCH", type: "NUMBER(4,2)")

			column(name: "PASTE_TO_AIR", type: "NUMBER(4,2)")

			column(name: "COMMENTS", type: "VARCHAR2(80 BYTE)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-50") {
		createTable(tableName: "MAT_CONC_FIELD_RESULTS") {
			column(name: "MNROAD_ID", type: "VARCHAR2(11 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "DATE_TESTED", type: "DATE")

			column(name: "SLUMP_INCH", type: "NUMBER(4,2)")

			column(name: "PERCENT_AIR", type: "NUMBER(4,2)")

			column(name: "UNIT_WEIGHT_PCF", type: "NUMBER(5,2)")

			column(name: "COMMENTS", type: "VARCHAR2(80 BYTE)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-51") {
		createTable(tableName: "MAT_CONC_FLEX_STRENGTH") {
			column(name: "MNROAD_ID", type: "VARCHAR2(30 BYTE)")

			column(name: "SAMPLE_ID", type: "VARCHAR2(30 BYTE)")

			column(name: "TEST_TYPE", type: "VARCHAR2(10 BYTE)")

			column(name: "DATE_TESTED", type: "DATE")

			column(name: "TEST_AGE_DAYS", type: "NUMBER(4,0)")

			column(name: "FLEX_STRENGTH_PSI", type: "NUMBER(6,0)")

			column(name: "DEPTH_IN", type: "NUMBER(5,2)")

			column(name: "LENGTH_IN", type: "NUMBER(5,2)")

			column(name: "WEIGHT_LBS", type: "NUMBER(5,2)")

			column(name: "COMMENTS", type: "VARCHAR2(100 BYTE)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-52") {
		createTable(tableName: "MAT_CONC_FREEZE_THAW_RESULTS") {
			column(name: "MNROAD_ID", type: "VARCHAR2(11 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "NUMBER_OF_CYCLES", type: "NUMBER(4,0)") {
				constraints(nullable: "false")
			}

			column(name: "DATE_TESTED", type: "DATE")

			column(name: "DATE_CAST", type: "DATE")

			column(name: "WEIGHT_CHANGE", type: "NUMBER(4,2)")

			column(name: "RELATIVE_DYNAMIC_MOD", type: "NUMBER(3,0)")

			column(name: "TOTAL_NMBR_CYCLES", type: "NUMBER(4,0)")

			column(name: "TOTAL_WEIGHT_CHANGE", type: "NUMBER(5,2)")

			column(name: "FINAL_REL_DYN_MOD", type: "NUMBER(3,0)")

			column(name: "COMMENTS", type: "VARCHAR2(80 BYTE)")

			column(name: "LENGTH", type: "NUMBER(5,2)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-53") {
		createTable(tableName: "MAT_CONC_MIX_GRAD_RESULTS") {
			column(name: "MNROAD_ID", type: "VARCHAR2(15 BYTE)")

			column(name: "LAB_SAMPLE_ID", type: "VARCHAR2(30 BYTE)")

			column(name: "AGGREGATE_TYPE", type: "VARCHAR2(15 BYTE)")

			column(name: "PIT_NUMBER", type: "VARCHAR2(15 BYTE)")

			column(name: "PIT_NAME", type: "VARCHAR2(50 BYTE)")

			column(name: "TESTING_LAB", type: "VARCHAR2(20 BYTE)")

			column(name: "PASSING_NUM_4", type: "NUMBER(6,2)")

			column(name: "PASSING_NUM_8", type: "NUMBER(6,2)")

			column(name: "PASSING_NUM_16", type: "NUMBER(6,2)")

			column(name: "PASSING_NUM_30", type: "NUMBER(6,2)")

			column(name: "PASSING_NUM_50", type: "NUMBER(6,2)")

			column(name: "PASSING_NUM_100", type: "NUMBER(6,2)")

			column(name: "PASSING_NUM_200", type: "NUMBER(6,2)")

			column(name: "PASSING_1_IN", type: "NUMBER(6,2)")

			column(name: "PASSING_1_12_IN", type: "NUMBER(6,2)")

			column(name: "PASSING_2_IN", type: "NUMBER(6,2)")

			column(name: "PASSING_34_IN", type: "NUMBER(6,2)")

			column(name: "PASSING_12_IN", type: "NUMBER(6,2)")

			column(name: "PASSING_38_IN", type: "NUMBER(6,2)")

			column(name: "FINENESS_MODULUS", type: "NUMBER(6,2)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-54") {
		createTable(tableName: "MAT_CONC_MOD_POISSON_RESULTS") {
			column(name: "MNROAD_ID", type: "VARCHAR2(11 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "DATE_TESTED", type: "DATE")

			column(name: "TEST_AGE_DAYS", type: "NUMBER(4,0)")

			column(name: "STATIC_MODULUS_ELASTICITY_PSI", type: "NUMBER(8,0)")

			column(name: "POISSON_RATIO", type: "NUMBER(4,2)")

			column(name: "AVG_STATIC_MOD_ELASTICITY_PSI", type: "NUMBER(8,0)")

			column(name: "AVERAGE_POISSON_RATIO", type: "NUMBER(4,2)")

			column(name: "DIAMETER_IN", type: "NUMBER(7,3)")

			column(name: "CAPPED_LENGTH_IN", type: "NUMBER(7,3)")

			column(name: "COMMENTS", type: "VARCHAR2(80 BYTE)")

			column(name: "DYNAMIC_MODULUS_ELASTICITY_PSI", type: "NUMBER(8,0)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-55") {
		createTable(tableName: "MAT_CONC_RAPID_CHLORIDE") {
			column(name: "MNROAD_ID", type: "VARCHAR2(30 BYTE)")

			column(name: "SAMPLE_NO", type: "VARCHAR2(30 BYTE)")

			column(name: "TRIAL", type: "NUMBER(3,0)")

			column(name: "SAMPLE_DESCRIPTION", type: "VARCHAR2(50 BYTE)")

			column(name: "SAMPLE_DIAMETER_IN", type: "NUMBER(3,0)")

			column(name: "SAMPLE_LENGTH_IN", type: "NUMBER(3,0)")

			column(name: "DATE_CAST", type: "DATE")

			column(name: "DATE_TESTED", type: "DATE")

			column(name: "AGE_DAYS", type: "NUMBER(3,0)")

			column(name: "COULOMBS", type: "NUMBER(5,0)")

			column(name: "MILLIAMPS_MAX", type: "NUMBER(6,1)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-56") {
		createTable(tableName: "MAT_CONC_STRENGTH_RESULTS") {
			column(name: "MNROAD_ID", type: "VARCHAR2(11 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "TEST_TYPE", type: "VARCHAR2(4 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "DATE_TESTED", type: "DATE")

			column(name: "LAB_ID", type: "VARCHAR2(30 BYTE)")

			column(name: "TEST_AGE_DAYS", type: "NUMBER(6,2)")

			column(name: "STRENGTH_PSI", type: "NUMBER(4,0)")

			column(name: "AVE_STRENGTH_PSI", type: "NUMBER(4,0)")

			column(name: "DIAMETER_IN", type: "NUMBER(4,2)")

			column(name: "LENGTH_IN", type: "NUMBER(4,2)")

			column(name: "CAPPED_LENGTH_IN", type: "NUMBER(4,2)")

			column(name: "COMMENTS", type: "VARCHAR2(80 BYTE)")

			column(name: "WEIGHT_LBS", type: "NUMBER(5,2)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-57") {
		createTable(tableName: "MAT_CONC_THERMAL_EXPANSION") {
			column(name: "MNROAD_ID", type: "VARCHAR2(30 BYTE)")

			column(name: "SAMPLE_ID", type: "VARCHAR2(30 BYTE)")

			column(name: "CTE_PER_DEGC", type: "NUMBER(5,1)")

			column(name: "COMMENTS", type: "VARCHAR2(50 BYTE)")

			column(name: "DIAMETER_IN", type: "NUMBER(3,0)")

			column(name: "LENGTH_IN", type: "NUMBER(3,0)")

			column(name: "DATE_CAST", type: "DATE")

			column(name: "DATE_TESTED", type: "DATE")

			column(name: "TEST_AGE_DAYS", type: "NUMBER")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-58") {
		createTable(tableName: "MAT_CONCRETE_MIX_DESIGNS") {
			column(name: "CELL", type: "NUMBER(3,0)") {
				constraints(nullable: "false")
			}

			column(name: "MIX_DESIGNATIONS", type: "VARCHAR2(45 BYTE)")

			column(name: "PORTLAND_CEMENT", type: "VARCHAR2(45 BYTE)")

			column(name: "FLY_ASH", type: "VARCHAR2(45 BYTE)")

			column(name: "OTHER_CEMENTITIOUS", type: "VARCHAR2(45 BYTE)")

			column(name: "WATER", type: "VARCHAR2(15 BYTE)")

			column(name: "WATER_TO_CEMENTITIOUS_RATIO", type: "NUMBER(3,2)")

			column(name: "FINE_AGGREGATE", type: "VARCHAR2(45 BYTE)")

			column(name: "OTHER_FINE_AGGREGATE", type: "VARCHAR2(50 BYTE)")

			column(name: "COARSE_AGGREGATE_1_AND_HALF_IN", type: "VARCHAR2(100 BYTE)")

			column(name: "COARSE_AGG_3_QUARTERS_IN_PLUS", type: "VARCHAR2(100 BYTE)")

			column(name: "COARSE_AGG_3_QUARTERS_IN_MINUS", type: "VARCHAR2(100 BYTE)")

			column(name: "OTHER_COARSE_AGGREGATE", type: "VARCHAR2(100 BYTE)")

			column(name: "AIR_CONTENT", type: "VARCHAR2(15 BYTE)")

			column(name: "ADMIXTURE_1", type: "VARCHAR2(100 BYTE)")

			column(name: "ADMIXTURE_2", type: "VARCHAR2(100 BYTE)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-59") {
		createTable(tableName: "MAT_CORE_LENGTHS") {
			column(name: "MNROAD_ID", type: "VARCHAR2(11 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "CORE_LENGTH_LAB_IN", type: "NUMBER(6,3)")

			column(name: "SPEC_LENGTH_IN", type: "NUMBER(6,3)")

			column(name: "DEG_0_LENGTH_IN", type: "NUMBER(6,3)")

			column(name: "DEG_90_LENGTH_IN", type: "NUMBER(6,3)")

			column(name: "DEG_180_LENGTH_IN", type: "NUMBER(6,3)")

			column(name: "DEG_270_LENGTH_IN", type: "NUMBER(6,3)")

			column(name: "COMMENTS", type: "VARCHAR2(250 BYTE)")

			column(name: "DATE_UPDATED", type: "DATE")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-60") {
		createTable(tableName: "MAT_HMA_AGING") {
			column(name: "MNROAD_ID", type: "VARCHAR2(11 BYTE)")

			column(name: "TESTING_LAB", type: "VARCHAR2(36 BYTE)")

			column(name: "TEST_DATE", type: "DATE")

			column(name: "SAM_ID", type: "VARCHAR2(16 BYTE)")

			column(name: "FIELD_ID", type: "VARCHAR2(60 BYTE)")

			column(name: "HMA_LIFT", type: "VARCHAR2(12 BYTE)")

			column(name: "DEPTH_FROM_TOP_IN", type: "NUMBER(6,3)")

			column(name: "SAMPLE_THICKNESS_IN", type: "NUMBER(6,3)")

			column(name: "HIGH_PG_TEMP_C", type: "NUMBER(5,2)")

			column(name: "AC_PERCENT", type: "NUMBER(5,2)")

			column(name: "COMMENTS", type: "VARCHAR2(60 BYTE)")

			column(name: "SIEVE_5_8INCH", type: "NUMBER(5,2)")

			column(name: "SIEVE_1_2INCH", type: "NUMBER(5,2)")

			column(name: "SIEVE_3_8INCH", type: "NUMBER(5,2)")

			column(name: "SIEVE_NUM_4", type: "NUMBER(5,2)")

			column(name: "SIEVE_NUM_8", type: "NUMBER(5,2)")

			column(name: "SIEVE_NUM_10", type: "NUMBER(5,2)")

			column(name: "SIEVE_NUM_16", type: "NUMBER(5,2)")

			column(name: "SIEVE_NUM_40", type: "NUMBER(5,2)")

			column(name: "SIEVE_NUM_50", type: "NUMBER(5,2)")

			column(name: "SIEVE_NUM_100", type: "NUMBER(5,2)")

			column(name: "SIEVE_NUM_200", type: "NUMBER(5,2)")

			column(name: "DATE_UPDATED", type: "DATE")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-61") {
		createTable(tableName: "MAT_HMA_APA") {
			column(name: "MNROAD_ID", type: "VARCHAR2(11 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "TESTING_LAB", type: "VARCHAR2(36 BYTE)")

			column(name: "TEST_DATE", type: "DATE")

			column(name: "SAMPLE_NAME", type: "VARCHAR2(24 BYTE)")

			column(name: "SAMPLE_TYPE", type: "VARCHAR2(24 BYTE)")

			column(name: "POSITION", type: "VARCHAR2(24 BYTE)")

			column(name: "AIR_VOIDS_PERCENT", type: "NUMBER(6,2)")

			column(name: "TEMP_C", type: "NUMBER(4,0)")

			column(name: "STROKES", type: "NUMBER(6,0)")

			column(name: "RUT_MM", type: "NUMBER(6,2)")

			column(name: "COMMENTS", type: "VARCHAR2(250 BYTE)")

			column(name: "DATE_UPDATED", type: "DATE")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-62") {
		createTable(tableName: "MAT_HMA_BBR_TEST") {
			column(name: "MNROAD_ID", type: "VARCHAR2(10 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "TESTING_LAB", type: "VARCHAR2(50 BYTE)")

			column(name: "SPECIMEN", type: "VARCHAR2(50 BYTE)")

			column(name: "TEST_DATE", type: "DATE")

			column(name: "TARGET_TEMP_C", type: "NUMBER(8,1)")

			column(name: "SOAK_TIME_MINUTES", type: "NUMBER(6,0)")

			column(name: "LENGTH_MM", type: "NUMBER(8,1)")

			column(name: "WIDTH_MM", type: "NUMBER(8,2)")

			column(name: "THICK_MM", type: "NUMBER(8,2)")

			column(name: "TIME_SEC", type: "NUMBER(8,1)")

			column(name: "STIFFNESS_MPA", type: "NUMBER(10,1)")

			column(name: "M_VALUE", type: "NUMBER(8,3)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-63") {
		createTable(tableName: "MAT_HMA_COMPLEX_SHEAR_MODU") {
			column(name: "MAT_HMA_COMPLEX_SHEAR_MODU_PK", type: "NUMBER(22,0)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "MAT_HMA_COMPLEX_SHEAR_MODU_PKC", primaryKeyTablespace: "USERS")
			}

			column(name: "MNROAD_ID", type: "VARCHAR2(11 BYTE)")

			column(name: "TESTING_LAB", type: "VARCHAR2(36 BYTE)")

			column(name: "TEST_DATE", type: "DATE")

			column(name: "SAMPLE_DESC", type: "VARCHAR2(40 BYTE)")

			column(name: "TEMP_F", type: "NUMBER(4,1)")

			column(name: "FREQUENCY_HZ", type: "NUMBER(6,3)")

			column(name: "MICROSTRAIN", type: "NUMBER(5,1)")

			column(name: "GSTAR_PSI", type: "NUMBER(7,0)")

			column(name: "PHASE_ANGLE_DEG", type: "NUMBER(3,1)")

			column(name: "COMMENTS", type: "VARCHAR2(100 BYTE)")

			column(name: "CREATE_USER", type: "VARCHAR2(8 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "CREATE_DATE", type: "DATE") {
				constraints(nullable: "false")
			}

			column(name: "MODIFY_USER", type: "VARCHAR2(8 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "MODIFY_DATE", type: "DATE") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-64") {
		createTable(tableName: "MAT_HMA_CORE_TESTS") {
			column(name: "MNROAD_ID", type: "VARCHAR2(11 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "TEST_DATE", type: "DATE")

			column(name: "LOCATION_DESC", type: "VARCHAR2(15 BYTE)")

			column(name: "LAB", type: "VARCHAR2(30 BYTE)")

			column(name: "LIFT_NUMBER", type: "NUMBER(2,0)")

			column(name: "LIFT_DESC", type: "VARCHAR2(25 BYTE)")

			column(name: "LIFT_THICKNESS_IN", type: "NUMBER(5,3)")

			column(name: "MAX_SPG_USED", type: "NUMBER(4,3)")

			column(name: "MAX_SPG_SOURCE", type: "VARCHAR2(40 BYTE)")

			column(name: "INP_GMB", type: "NUMBER(4,3)")

			column(name: "INP_VOIDS_PERCENT", type: "NUMBER(4,2)")

			column(name: "COMMENTS", type: "VARCHAR2(250 BYTE)")

			column(name: "DATE_UPDATED", type: "DATE")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-65") {
		createTable(tableName: "MAT_HMA_DCT_TEST") {
			column(name: "MAT_HMA_DCT_TEST_PK", type: "NUMBER(22,0)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "MAT_HMA_DCT_TEST_PKC", primaryKeyTablespace: "USERS")
			}

			column(name: "MNROAD_ID", type: "VARCHAR2(11 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "TESTING_LAB", type: "VARCHAR2(36 BYTE)")

			column(name: "TEST_DATE", type: "DATE")

			column(name: "SAMPLE_DESC", type: "VARCHAR2(40 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "TEMP_C", type: "NUMBER(4,1)")

			column(name: "FRACTURE_ENERGY_J_PER_M2", type: "NUMBER(6,1)")

			column(name: "COMMENTS", type: "VARCHAR2(100 BYTE)")

			column(name: "CREATE_USER", type: "VARCHAR2(8 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "CREATE_DATE", type: "DATE") {
				constraints(nullable: "false")
			}

			column(name: "MODIFY_USER", type: "VARCHAR2(8 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "MODIFY_DATE", type: "DATE") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-66") {
		createTable(tableName: "MAT_HMA_DILATOMETRIC_TEST") {
			column(name: "MAT_HMA_DILATOMETRIC_TEST_PK", type: "NUMBER(22,0)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "MAT_HMA_DILATOMETRIC_TEST_PKC", primaryKeyTablespace: "USERS")
			}

			column(name: "MNROAD_ID", type: "VARCHAR2(11 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "TESTING_LAB", type: "VARCHAR2(36 BYTE)")

			column(name: "TEST_DATE", type: "DATE")

			column(name: "SAMPLE_DESC", type: "VARCHAR2(40 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "THERMAL_CYCLE", type: "VARCHAR2(25 BYTE)")

			column(name: "GLASS_TRANS_TEMP_C", type: "NUMBER(4,1)")

			column(name: "COEFF_BELOW_TG", type: "NUMBER(10,8)")

			column(name: "COEFF_ABOVE_TG", type: "NUMBER(10,8)")

			column(name: "COMMENTS", type: "VARCHAR2(100 BYTE)")

			column(name: "CREATE_USER", type: "VARCHAR2(8 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "CREATE_DATE", type: "DATE") {
				constraints(nullable: "false")
			}

			column(name: "MODIFY_USER", type: "VARCHAR2(8 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "MODIFY_DATE", type: "DATE") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-67") {
		createTable(tableName: "MAT_HMA_DYNAMIC_MODULUS") {
			column(name: "MNROAD_ID", type: "VARCHAR2(11 BYTE)")

			column(name: "TESTING_LAB", type: "VARCHAR2(36 BYTE)")

			column(name: "TEST_DATE", type: "DATE")

			column(name: "SAMPLE_NO", type: "NUMBER(4,0)")

			column(name: "AIR_VOIDS_PERCENT", type: "NUMBER(4,1)")

			column(name: "HEIGHT_MM", type: "NUMBER(6,2)")

			column(name: "DIAMETER_MM", type: "NUMBER(6,2)")

			column(name: "TEMP_C", type: "NUMBER(4,2)")

			column(name: "FREQUENCY_HZ", type: "NUMBER(6,3)")

			column(name: "DYNAMIC_MODULUS_GPA", type: "NUMBER(4,1)")

			column(name: "PHASE_ANGLE_DEG", type: "NUMBER(4,1)")

			column(name: "DATE_UPDATED", type: "DATE")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-68") {
		createTable(tableName: "MAT_HMA_FLOW_NUMBER") {
			column(name: "MNROAD_ID", type: "VARCHAR2(10 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "TESTING_LAB", type: "VARCHAR2(50 BYTE)")

			column(name: "TEST_DATE", type: "DATE")

			column(name: "SAMPLE_DESCRIPTION", type: "VARCHAR2(50 BYTE)")

			column(name: "SPECIMEN", type: "VARCHAR2(50 BYTE)")

			column(name: "TEMP_C", type: "NUMBER(6,1)")

			column(name: "CONFINING_PRESSURE_KPA", type: "NUMBER(6,1)")

			column(name: "FLOW_NUMBER_NCHRP", type: "NUMBER(4,0)")

			column(name: "TOTAL_STRAIN_NCHRP", type: "NUMBER(6,0)")

			column(name: "TOTAL_CYCLES_NCHRP", type: "NUMBER(4,0)")

			column(name: "FLOW_NUMBER_FRANKEN", type: "NUMBER(4,0)")

			column(name: "TOTAL_STRAIN_FRANKEN", type: "NUMBER(6,0)")

			column(name: "TOTAL_CYCLES_FRANKEN", type: "NUMBER(4,0)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-69") {
		createTable(tableName: "MAT_HMA_HAMBURG") {
			column(name: "MNROAD_ID", type: "VARCHAR2(10 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "TESTING_LAB", type: "VARCHAR2(50 BYTE)")

			column(name: "TEST_DATE", type: "DATE")

			column(name: "SAMPLE_DESCRIPTION", type: "VARCHAR2(50 BYTE)")

			column(name: "CONDITION", type: "VARCHAR2(50 BYTE)")

			column(name: "TEMP_C", type: "NUMBER(6,1)")

			column(name: "NO_PASSES", type: "NUMBER(8,0)")

			column(name: "RUT_DEPTH_MM", type: "NUMBER(6,1)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-70") {
		createTable(tableName: "MAT_HMA_IDT_TEST") {
			column(name: "MNROAD_ID", type: "VARCHAR2(20 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "TEST_DATE", type: "DATE") {
				constraints(nullable: "false")
			}

			column(name: "TESTING_LAB", type: "VARCHAR2(30 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "SAMPLE_DESC", type: "VARCHAR2(30 BYTE)")

			column(name: "TEMP_C", type: "NUMBER(5,1)")

			column(name: "TENSILE_STRESS_PA", type: "NUMBER(10,0)")

			column(name: "MODULUS_PA", type: "NUMBER(14,0)")

			column(name: "LOADING_SEC", type: "NUMBER(4,0)")

			column(name: "CREEP_COMPLIANCE_1_PA", type: "NUMBER(16,14)")

			column(name: "M_VALUE", type: "NUMBER(7,5)")

			column(name: "POISSONS_RATIO", type: "NUMBER(7,5)")

			column(name: "COMMENTS", type: "VARCHAR2(100 BYTE)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-71") {
		createTable(tableName: "MAT_HMA_INDIRECT_TENS_FATI") {
			column(name: "MAT_HMA_INDIRECT_TENS_FATI_PK", type: "NUMBER(22,0)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "MAT_HMA_INDIRECT_TENS_FATI_PKC", primaryKeyTablespace: "USERS")
			}

			column(name: "MNROAD_ID", type: "VARCHAR2(11 BYTE)")

			column(name: "TESTING_LAB", type: "VARCHAR2(36 BYTE)")

			column(name: "TEST_DATE", type: "DATE")

			column(name: "SAMPLE_DESC", type: "VARCHAR2(40 BYTE)")

			column(name: "HEIGHT_INCH", type: "NUMBER(4,2)")

			column(name: "LOAD_LBS", type: "NUMBER(6,1)")

			column(name: "TENSILE_STRESS_PSI", type: "NUMBER(5,1)")

			column(name: "STRESS_STRENGTH_RATIO", type: "NUMBER(4,3)")

			column(name: "INITIAL_MICROSTRAIN", type: "NUMBER(5,1)")

			column(name: "CYCLES_TO_FAILURE", type: "NUMBER(7,0)")

			column(name: "CYCLES_TO_HALF_MR", type: "NUMBER(7,0)")

			column(name: "INITIAL_RESILIENT_MOD_KSI", type: "NUMBER(6,1)")

			column(name: "TEMP_F", type: "NUMBER(4,1)")

			column(name: "COMMENTS", type: "VARCHAR2(100 BYTE)")

			column(name: "CREATE_USER", type: "VARCHAR2(8 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "CREATE_DATE", type: "DATE") {
				constraints(nullable: "false")
			}

			column(name: "MODIFY_USER", type: "VARCHAR2(8 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "MODIFY_DATE", type: "DATE") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-72") {
		createTable(tableName: "MAT_HMA_MIX_TESTS") {
			column(name: "MNROAD_ID", type: "VARCHAR2(11 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "TESTING_LAB", type: "VARCHAR2(40 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "TEST_DATE", type: "DATE") {
				constraints(nullable: "false")
			}

			column(name: "NUMBER_BLOWS", type: "NUMBER(2,0)")

			column(name: "BULK_SP_GRAVITY", type: "NUMBER(4,3)")

			column(name: "RICE_SP_GRAVITY", type: "NUMBER(4,3)")

			column(name: "AIR_VOIDS_PERCENT", type: "NUMBER(4,1)")

			column(name: "VMA_PERCENT", type: "NUMBER(3,1)")

			column(name: "STABILITY", type: "NUMBER(4,0)")

			column(name: "FLOW", type: "NUMBER(3,1)")

			column(name: "EXTRACTED_AC_PERCENT", type: "NUMBER(4,1)")

			column(name: "INCINERATOR_AC_PERCENT", type: "NUMBER(4,1)")

			column(name: "TENSILE_STRENGTH_RATIO", type: "NUMBER(3,0)")

			column(name: "FINE_AGG_ANGULARITY", type: "VARCHAR2(10 BYTE)")

			column(name: "COARSE_AGG_ANGULARITY", type: "VARCHAR2(10 BYTE)")

			column(name: "MIX_MOISTURE_PERCENT", type: "NUMBER(3,2)")

			column(name: "COMMENTS", type: "VARCHAR2(100 BYTE)")

			column(name: "DATE_UPDATED", type: "DATE")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-73") {
		createTable(tableName: "MAT_HMA_ORIGINAL_DENSITY_AIR") {
			column(name: "MNROAD_ID", type: "VARCHAR2(15 BYTE)")

			column(name: "DESCRIPTION", type: "VARCHAR2(50 BYTE)")

			column(name: "HMA_LIFT", type: "NUMBER(1,0)")

			column(name: "LIFT_DESCRIPTION", type: "VARCHAR2(10 BYTE)")

			column(name: "TESTING_LAB", type: "VARCHAR2(30 BYTE)")

			column(name: "LIFT_THICKNESS_INCHES", type: "NUMBER(5,3)")

			column(name: "BULK_SPECIFIC_GRAVITY", type: "NUMBER(5,3)")

			column(name: "THEO_MAX_SPECIFIC_GRAVITY", type: "NUMBER(5,3)")

			column(name: "AIR_VOID_PERCENT", type: "NUMBER(3,1)")

			column(name: "RESULTS_COMMENT", type: "VARCHAR2(75 BYTE)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-74") {
		createTable(tableName: "MAT_HMA_REPEAT_PERM_DEFORM") {
			column(name: "MAT_HMA_REPEAT_PERM_DEFORM_PK", type: "NUMBER(22,0)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "MAT_HMA_REPEAT_PERM_DEFORM_PKC", primaryKeyTablespace: "USERS")
			}

			column(name: "MNROAD_ID", type: "VARCHAR2(11 BYTE)")

			column(name: "TESTING_LAB", type: "VARCHAR2(36 BYTE)")

			column(name: "TEST_DATE", type: "DATE")

			column(name: "SAMPLE_DESC", type: "VARCHAR2(40 BYTE)")

			column(name: "AC_PERCENT", type: "NUMBER(4,2)")

			column(name: "AIR_VOIDS_PERCENT", type: "NUMBER(4,2)")

			column(name: "TEMP_F", type: "NUMBER(4,1)")

			column(name: "CONFINING_PRESSURE_PSI", type: "NUMBER(3,0)")

			column(name: "VERTICAL_STRESS_PSI", type: "NUMBER(3,0)")

			column(name: "INTERCEPT_A", type: "NUMBER(6,5)")

			column(name: "SLOPE_B", type: "NUMBER(6,5)")

			column(name: "RESILIENT_STRAIN_200_PERCENT", type: "NUMBER(6,5)")

			column(name: "FLOW_NUMBER", type: "NUMBER(5,0)")

			column(name: "TOTAL_NO_CYCLES", type: "NUMBER(5,0)")

			column(name: "COMMENTS", type: "VARCHAR2(100 BYTE)")

			column(name: "CREATE_USER", type: "VARCHAR2(8 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "CREATE_DATE", type: "DATE") {
				constraints(nullable: "false")
			}

			column(name: "MODIFY_USER", type: "VARCHAR2(8 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "MODIFY_DATE", type: "DATE") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-75") {
		createTable(tableName: "MAT_HMA_REPEAT_SHEAR") {
			column(name: "MAT_HMA_REPEAT_SHEAR_PK", type: "NUMBER(22,0)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "MAT_HMA_REPEAT_SHEAR_PKC", primaryKeyTablespace: "USERS")
			}

			column(name: "MNROAD_ID", type: "VARCHAR2(11 BYTE)")

			column(name: "TESTING_LAB", type: "VARCHAR2(36 BYTE)")

			column(name: "TEST_DATE", type: "DATE")

			column(name: "SAMPLE_DESC", type: "VARCHAR2(40 BYTE)")

			column(name: "AIR_VOIDS_PERCENT", type: "NUMBER(4,2)")

			column(name: "TEMP_F", type: "NUMBER(4,1)")

			column(name: "SHEAR_STRESS_PSI", type: "NUMBER(3,0)")

			column(name: "CYCLE_NO", type: "NUMBER(5,0)")

			column(name: "PERM_SHEAR_STRAIN_PERCENT", type: "NUMBER(7,4)")

			column(name: "COMMENTS", type: "VARCHAR2(100 BYTE)")

			column(name: "CREATE_USER", type: "VARCHAR2(8 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "CREATE_DATE", type: "DATE") {
				constraints(nullable: "false")
			}

			column(name: "MODIFY_USER", type: "VARCHAR2(8 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "MODIFY_DATE", type: "DATE") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-76") {
		createTable(tableName: "MAT_HMA_SCB_TEST") {
			column(name: "MNROAD_ID", type: "VARCHAR2(11 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "TESTING_LAB", type: "VARCHAR2(36 BYTE)")

			column(name: "TEST_DATE", type: "DATE")

			column(name: "SAMPLE_NAME", type: "VARCHAR2(24 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "TEMP_C", type: "NUMBER(4,0)")

			column(name: "THICKNESS_MM", type: "NUMBER(6,2)")

			column(name: "NOTCH_LENGTH_MM", type: "NUMBER(6,2)")

			column(name: "RADIUS_MM", type: "NUMBER(6,2)")

			column(name: "FRACTURE_ENERGY", type: "NUMBER(6,2)")

			column(name: "FRACTURE_TOUGHNESS", type: "NUMBER(6,2)")

			column(name: "STIFFNESS", type: "NUMBER(6,2)")

			column(name: "DATE_UPDATED", type: "DATE")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-77") {
		createTable(tableName: "MAT_HMA_SENB_TEST") {
			column(name: "MAT_HMA_SENB_TEST_PK", type: "NUMBER(22,0)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "MAT_HMA_SENB_TEST_PKC", primaryKeyTablespace: "USERS")
			}

			column(name: "MNROAD_ID", type: "VARCHAR2(11 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "SAMPLE_DESC", type: "VARCHAR2(40 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "TESTING_LAB", type: "VARCHAR2(36 BYTE)")

			column(name: "TEST_DATE", type: "DATE")

			column(name: "TEMP_C", type: "NUMBER(4,1)")

			column(name: "FRACTURE_ENERGY_J_PER_M2", type: "NUMBER(6,1)")

			column(name: "COMMENTS", type: "VARCHAR2(100 BYTE)")

			column(name: "CREATE_USER", type: "VARCHAR2(8 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "CREATE_DATE", type: "DATE") {
				constraints(nullable: "false")
			}

			column(name: "MODIFY_USER", type: "VARCHAR2(8 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "MODIFY_DATE", type: "DATE") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-78") {
		createTable(tableName: "MAT_HMA_SIEVE_DATA") {
			column(name: "MNROAD_ID", type: "VARCHAR2(11 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "LAB", type: "VARCHAR2(40 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "TEST_DATE", type: "DATE") {
				constraints(nullable: "false")
			}

			column(name: "SAMPLE_TYPE", type: "VARCHAR2(30 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "THREE_FOURTHS", type: "NUMBER(3,0)")

			column(name: "FIVE_EIGHTHS", type: "NUMBER(3,0)")

			column(name: "ONE_HALF", type: "NUMBER(3,0)")

			column(name: "THREE_EIGHTHS", type: "NUMBER(3,0)")

			column(name: "NO_4", type: "NUMBER(4,1)")

			column(name: "NO_8", type: "NUMBER(4,1)")

			column(name: "NO_10", type: "NUMBER(4,1)")

			column(name: "NO_20", type: "NUMBER(4,1)")

			column(name: "NO_40", type: "NUMBER(4,1)")

			column(name: "NO_80", type: "NUMBER(4,1)")

			column(name: "NO_200", type: "NUMBER(4,1)")

			column(name: "NO_16", type: "NUMBER(4,1)")

			column(name: "NO_30", type: "NUMBER(4,1)")

			column(name: "NO_50", type: "NUMBER(4,1)")

			column(name: "NO_100", type: "NUMBER(4,1)")

			column(name: "DATE_UPDATED", type: "DATE")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-79") {
		createTable(tableName: "MAT_HMA_TRIAXIAL_STATIC_CREEP") {
			column(name: "MAT_HMA_TRIAXIAL_STATIC_CR_PK", type: "NUMBER(22,0)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "MAT_HMA_TRIAXIAL_STATIC_CR_PKC", primaryKeyTablespace: "USERS")
			}

			column(name: "MNROAD_ID", type: "VARCHAR2(11 BYTE)")

			column(name: "TESTING_LAB", type: "VARCHAR2(36 BYTE)")

			column(name: "TEST_DATE", type: "DATE")

			column(name: "SAMPLE_DESC", type: "VARCHAR2(40 BYTE)")

			column(name: "AC_PERCENT", type: "NUMBER(4,2)")

			column(name: "AIR_VOIDS_PERCENT", type: "NUMBER(4,2)")

			column(name: "TEMP_F", type: "NUMBER(4,1)")

			column(name: "CONFINING_PRESSURE_PSI", type: "NUMBER(3,0)")

			column(name: "VERTICAL_STRESS_PSI", type: "NUMBER(3,0)")

			column(name: "INTERCEPT_A", type: "NUMBER(6,5)")

			column(name: "COMPLIANCE_AT_1SEC", type: "NUMBER(7,6)")

			column(name: "SLOPE_B", type: "NUMBER(7,5)")

			column(name: "FLOW_TIME_SEC", type: "NUMBER(5,0)")

			column(name: "COMMENTS", type: "VARCHAR2(100 BYTE)")

			column(name: "CREATE_USER", type: "VARCHAR2(8 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "CREATE_DATE", type: "DATE") {
				constraints(nullable: "false")
			}

			column(name: "MODIFY_USER", type: "VARCHAR2(8 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "MODIFY_DATE", type: "DATE") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-80") {
		createTable(tableName: "MAT_HMA_TRIAXIAL_STRENGTH") {
			column(name: "MAT_HMA_TRIAXIAL_STRENGTH_PK", type: "NUMBER(22,0)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "MAT_HMA_TRIAXIAL_STRENGTH_PKC", primaryKeyTablespace: "USERS")
			}

			column(name: "MNROAD_ID", type: "VARCHAR2(11 BYTE)")

			column(name: "TESTING_LAB", type: "VARCHAR2(36 BYTE)")

			column(name: "TEST_DATE", type: "DATE")

			column(name: "SAMPLE_DESC", type: "VARCHAR2(40 BYTE)")

			column(name: "AC_PERCENT", type: "NUMBER(4,2)")

			column(name: "AIR_VOIDS_PERCENT", type: "NUMBER(4,2)")

			column(name: "CONFINING_PRESSURE_PSI", type: "NUMBER(3,0)")

			column(name: "TEMP_F", type: "NUMBER(4,1)")

			column(name: "MAX_DEVIATORIC_STRESS_PSI", type: "NUMBER(5,1)")

			column(name: "MAX_NORMAL_STRESS_PSI", type: "NUMBER(5,1)")

			column(name: "PERCENT_STRAIN", type: "NUMBER(3,1)")

			column(name: "STRAIN_RATE_IN_PER_MIN", type: "NUMBER(5,2)")

			column(name: "COMMENTS", type: "VARCHAR2(100 BYTE)")

			column(name: "CREATE_USER", type: "VARCHAR2(8 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "CREATE_DATE", type: "DATE") {
				constraints(nullable: "false")
			}

			column(name: "MODIFY_USER", type: "VARCHAR2(8 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "MODIFY_DATE", type: "DATE") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-81") {
		createTable(tableName: "MAT_HMA_TSRST_TEST") {
			column(name: "MAT_HMA_TSRST_TEST_PK", type: "NUMBER(22,0)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "MAT_HMA_TSRST_TEST_PKC", primaryKeyTablespace: "USERS")
			}

			column(name: "MNROAD_ID", type: "VARCHAR2(11 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "TESTING_LAB", type: "VARCHAR2(36 BYTE)")

			column(name: "TEST_DATE", type: "DATE")

			column(name: "SAMPLE_DESC", type: "VARCHAR2(40 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "FRACTURE_TEMP_C", type: "NUMBER(4,1)")

			column(name: "FRACTURE_STRENGTH_MPA", type: "NUMBER(5,3)")

			column(name: "SLOPE_MPA_C", type: "NUMBER(5,3)")

			column(name: "TRANSITION_TEMP_C", type: "NUMBER(4,1)")

			column(name: "FAILURE_LOCATION", type: "VARCHAR2(20 BYTE)")

			column(name: "FAILURE_TYPE", type: "VARCHAR2(20 BYTE)")

			column(name: "COMMENTS", type: "VARCHAR2(100 BYTE)")

			column(name: "CREATE_USER", type: "VARCHAR2(8 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "CREATE_DATE", type: "DATE") {
				constraints(nullable: "false")
			}

			column(name: "MODIFY_USER", type: "VARCHAR2(8 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "MODIFY_DATE", type: "DATE") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-82") {
		createTable(tableName: "MAT_HMA_TTI_OVERLAY") {
			column(name: "MNROAD_ID", type: "VARCHAR2(11 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "TESTING_LAB", type: "VARCHAR2(36 BYTE)")

			column(name: "TEST_DATE", type: "DATE")

			column(name: "SAMPLE_DESC", type: "VARCHAR2(60 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "TEMP_C", type: "NUMBER(6,1)")

			column(name: "AIR_VOIDS_PERCENT", type: "NUMBER(4,0)")

			column(name: "START_LOAD_LBS", type: "NUMBER(5,0)")

			column(name: "FINAL_LOAD_LBS", type: "NUMBER(4,0)")

			column(name: "PERCENT_LOAD_DECLINE", type: "NUMBER(4,0)")

			column(name: "NO_CYCLES", type: "NUMBER(5,0)")

			column(name: "COMMENTS", type: "VARCHAR2(200 BYTE)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-83") {
		createTable(tableName: "MAT_HMA_ULTRASONIC_MODULUS") {
			column(name: "MAT_HMA_ULTRASONIC_MODULUS_PK", type: "NUMBER(22,0)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "MAT_HMA_ULTRASONIC_MODULUS_PKC", primaryKeyTablespace: "USERS")
			}

			column(name: "MNROAD_ID", type: "VARCHAR2(11 BYTE)")

			column(name: "TESTING_LAB", type: "VARCHAR2(36 BYTE)")

			column(name: "TEST_DATE", type: "DATE")

			column(name: "SAMPLE_DESC", type: "VARCHAR2(40 BYTE)")

			column(name: "HEIGHT_MM", type: "NUMBER(6,2)")

			column(name: "DENSITY_KG_M3", type: "NUMBER(6,2)")

			column(name: "AIR_VOIDS_PERCENT", type: "NUMBER(4,2)")

			column(name: "TEMP_F", type: "NUMBER(4,1)")

			column(name: "ELASTIC_MODULUS_PSI", type: "NUMBER(9,0)")

			column(name: "COMMENTS", type: "VARCHAR2(100 BYTE)")

			column(name: "CREATE_USER", type: "VARCHAR2(8 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "CREATE_DATE", type: "DATE") {
				constraints(nullable: "false")
			}

			column(name: "MODIFY_USER", type: "VARCHAR2(8 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "MODIFY_DATE", type: "DATE") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-84") {
		createTable(tableName: "MAT_PROCTOR_CURVES") {
			column(name: "PROCTOR_CURVE", type: "VARCHAR2(14 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "OPTIMUM_MC_PCT_DRY_WT", type: "NUMBER(4,1)") {
				constraints(nullable: "false")
			}

			column(name: "MAXIMUM_DRY_DENSITY_PCF", type: "NUMBER(4,1)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-85") {
		createTable(tableName: "MAT_SAMPLES") {
			column(name: "ID", type: "NUMBER(19,0)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "SYS_C004595", primaryKeyTablespace: "USERS")
			}

			column(name: "VERSION", type: "NUMBER(19,0)") {
				constraints(nullable: "false")
			}

			column(name: "MNROAD_ID", type: "VARCHAR2(11 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "CELL", type: "NUMBER(10,0)")

			column(name: "STATION", type: "NUMBER(16,8)")

			column(name: "OFFSET", type: "NUMBER(8,2)")

			column(name: "SAMPLE_DATE", type: "TIMESTAMP(6)")

			column(name: "MATERIAL_GROUP", type: "VARCHAR2(40 BYTE)")

			column(name: "CONTAINER_TYPE", type: "VARCHAR2(30 BYTE)")

			column(name: "STORAGE_LOCATION", type: "VARCHAR2(50 BYTE)")

			column(name: "COMMENTS", type: "VARCHAR2(250 BYTE)")

			column(name: "FIELD_ID", type: "VARCHAR2(25 BYTE)")

			column(name: "CONTACT_PERSON", type: "VARCHAR2(24 BYTE)")

			column(name: "COURSE", type: "VARCHAR2(8 BYTE)")

			column(name: "LIFT_NUMBER", type: "NUMBER(10,0)")

			column(name: "DEPTH_CODE", type: "VARCHAR2(40 BYTE)")

			column(name: "SAMPLE_DEPTH_TOP", type: "NUMBER(8,2)")

			column(name: "SAMPLE_DEPTH_BOTTOM", type: "NUMBER(8,2)")

			column(name: "SAMPLE_TIME", type: "VARCHAR2(30 BYTE)")

			column(name: "SAMPLE_CURE_TIME", type: "NUMBER(7,2)")

			column(name: "SPEC", type: "VARCHAR2(15 BYTE)")

			column(name: "DATE_CREATED", type: "TIMESTAMP(6)") {
				constraints(nullable: "false")
			}

			column(name: "LAST_UPDATED", type: "TIMESTAMP(6)") {
				constraints(nullable: "false")
			}

			column(name: "CREATED_BY", type: "VARCHAR2(11 BYTE)")

			column(name: "LAST_UPDATED_BY", type: "VARCHAR2(11 BYTE)")

			column(name: "LAYER_ID", type: "NUMBER(19,0)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-86") {
		createTable(tableName: "MAT_SOIL_TESTS") {
			column(name: "MNROAD_ID", type: "VARCHAR2(11 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "LAB_SAMPLE_ID", type: "VARCHAR2(15 BYTE)")

			column(name: "TEST_TYPE", type: "VARCHAR2(4 BYTE)")

			column(name: "SUBTEST_NAME", type: "VARCHAR2(40 BYTE)")

			column(name: "TEST_DATE", type: "DATE")

			column(name: "TEST_LAB_NAME", type: "VARCHAR2(40 BYTE)")

			column(name: "PROCTOR_CURVE", type: "VARCHAR2(3 BYTE)")

			column(name: "TEST_RESULT", type: "NUMBER(12,6)")

			column(name: "TEST_RESULT_CHAR", type: "VARCHAR2(12 BYTE)")

			column(name: "DATE_UPDATED", type: "DATE")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-87") {
		createTable(tableName: "MAT_TEST_TYPES") {
			column(name: "TEST_TYPE", type: "VARCHAR2(4 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "TEST_TYPE_DESC", type: "VARCHAR2(80 BYTE)")

			column(name: "SUBTEST_NAME", type: "VARCHAR2(40 BYTE)")

			column(name: "AASHTO_DESIGNATION", type: "VARCHAR2(60 BYTE)")

			column(name: "RESULTS_UNITS", type: "VARCHAR2(60 BYTE)")

			column(name: "COMMENTS", type: "VARCHAR2(500 BYTE)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-88") {
		createTable(tableName: "MAT_UNBOUND_FIELD_MOISTURE") {
			column(name: "CELL", type: "NUMBER(3,0)") {
				constraints(nullable: "false")
			}

			column(name: "DATE_SAMPLE", type: "DATE") {
				constraints(nullable: "false")
			}

			column(name: "DATE_TEST", type: "DATE") {
				constraints(nullable: "false")
			}

			column(name: "STATION", type: "NUMBER(8,1)")

			column(name: "OFFSET_FT", type: "NUMBER(6,1)")

			column(name: "MOISTURE_TEST_NO", type: "VARCHAR2(5 BYTE)")

			column(name: "MATERIAL_TESTED", type: "VARCHAR2(50 BYTE)")

			column(name: "MOISTURE_CONTENT_PCT", type: "NUMBER(6,1)")

			column(name: "COMMENTS", type: "VARCHAR2(50 BYTE)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-89") {
		createTable(tableName: "MAT_UNBOUND_GRADATIONS") {
			column(name: "MNROAD_ID", type: "VARCHAR2(12 BYTE)")

			column(name: "TEST_DATE", type: "DATE")

			column(name: "TESTING_LAB", type: "VARCHAR2(40 BYTE)")

			column(name: "LAB_SAMPLE_ID", type: "VARCHAR2(15 BYTE)")

			column(name: "PASSING_2_IN", type: "NUMBER(6,1)")

			column(name: "PASSING_1_AND_HALF_IN", type: "NUMBER(6,1)")

			column(name: "PASSING_1_AND_QUARTER_IN", type: "NUMBER(6,1)")

			column(name: "PASSING_1_IN", type: "NUMBER(6,1)")

			column(name: "PASSING_3_QUARTERS_IN", type: "NUMBER(6,1)")

			column(name: "PASSING_5_EIGHTHS_IN", type: "NUMBER(6,1)")

			column(name: "PASSING_HALF_IN", type: "NUMBER(6,1)")

			column(name: "PASSING_3_EIGHTS_IN", type: "NUMBER(6,1)")

			column(name: "PASSING_NUM_4", type: "NUMBER(6,1)")

			column(name: "PASSING_NUM_8", type: "NUMBER(6,1)")

			column(name: "PASSING_NUM_10", type: "NUMBER(6,1)")

			column(name: "PASSING_NUM_16", type: "NUMBER(6,1)")

			column(name: "PASSING_NUM_20", type: "NUMBER(6,1)")

			column(name: "PASSING_NUM_30", type: "NUMBER(6,1)")

			column(name: "PASSING_NUM_40", type: "NUMBER(6,1)")

			column(name: "PASSING_NUM_50", type: "NUMBER(6,1)")

			column(name: "PASSING_NUM_60", type: "NUMBER(6,1)")

			column(name: "PASSING_NUM_100", type: "NUMBER(6,1)")

			column(name: "PASSING_NUM_200", type: "NUMBER(6,1)")

			column(name: "PASSING_2_MIN", type: "NUMBER(6,1)")

			column(name: "PASSING_5_MIN", type: "NUMBER(6,1)")

			column(name: "PASSING_15_MIN", type: "NUMBER(6,1)")

			column(name: "PASSING_30_MIN", type: "NUMBER(6,1)")

			column(name: "PASSING_60_MIN", type: "NUMBER(6,1)")

			column(name: "PASSING_250_MIN", type: "NUMBER(6,1)")

			column(name: "PASSING_24_HR", type: "NUMBER(6,1)")

			column(name: "MM_DIAM_2_MIN", type: "NUMBER(7,4)")

			column(name: "MM_DIAM_5_MIN", type: "NUMBER(7,4)")

			column(name: "MM_DIAM_15_MIN", type: "NUMBER(7,4)")

			column(name: "MM_DIAM_30_MIN", type: "NUMBER(7,4)")

			column(name: "MM_DIAM_60_MIN", type: "NUMBER(7,4)")

			column(name: "MM_DIAM_250_MIN", type: "NUMBER(7,4)")

			column(name: "MM_DIAM_24_HR", type: "NUMBER(7,4)")

			column(name: "DATE_UPDATED", type: "DATE")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-90") {
		createTable(tableName: "MAT_UNBOUND_TUBE_SUCTION") {
			column(name: "MNROAD_ID", type: "VARCHAR2(20 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "SAMPLE_NO", type: "VARCHAR2(30 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "TEST_DATE", type: "DATE") {
				constraints(nullable: "false")
			}

			column(name: "HOURS", type: "NUMBER(4,1)") {
				constraints(nullable: "false")
			}

			column(name: "DIELECTRIC_VALUE", type: "NUMBER(6,1)")

			column(name: "CONDUCTIVITY", type: "NUMBER(6,0)")

			column(name: "MOISTURE_CONTENT_PERCENT", type: "NUMBER(6,1)")

			column(name: "ACT_WET_DENSITY_PCF", type: "NUMBER(6,1)")

			column(name: "ACT_DRY_DENSITY_PCF", type: "NUMBER(6,1)")

			column(name: "RELATIVE_DENSITY", type: "NUMBER(6,1)")

			column(name: "COMMENTS", type: "VARCHAR2(100 BYTE)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-91") {
		createTable(tableName: "MATERIAL") {
			column(name: "ID", type: "NUMBER(19,0)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "SYS_C004310", primaryKeyTablespace: "USERS")
			}

			column(name: "VERSION", type: "NUMBER(19,0)") {
				constraints(nullable: "false")
			}

			column(name: "BASIC_MATERIAL", type: "VARCHAR2(255 BYTE)")

			column(name: "DESCRIPTION", type: "VARCHAR2(255 BYTE)")

			column(name: "COMPONENT_MODIFICATION", type: "VARCHAR2(255 BYTE)")

			column(name: "DISPLAY_NAME", type: "VARCHAR2(254 BYTE)")

			column(name: "MN_DOT_SPEC_NUMBER", type: "VARCHAR2(20 BYTE)")

			column(name: "PROCESS_MODIFICATION", type: "VARCHAR2(255 BYTE)")

			column(name: "SPEC_YEAR", type: "NUMBER(10,0)")

			column(name: "DATE_CREATED", type: "TIMESTAMP(6)") {
				constraints(nullable: "false")
			}

			column(name: "LAST_UPDATED", type: "TIMESTAMP(6)") {
				constraints(nullable: "false")
			}

			column(name: "BINDER", type: "VARCHAR2(10 BYTE)")

			column(name: "MODIFIER", type: "VARCHAR2(250 BYTE)")

			column(name: "FIBER_TYPE", type: "VARCHAR2(30 BYTE)")

			column(name: "DESIGN_LEVEL", type: "VARCHAR2(20 BYTE)")

			column(name: "COURSE", type: "VARCHAR2(10 BYTE)")

			column(name: "PERCENT_RAP", type: "NUMBER(10,0)")

			column(name: "GRADATION_NAME", type: "VARCHAR2(50 BYTE)")

			column(name: "CREATED_BY", type: "VARCHAR2(11 BYTE)")

			column(name: "LAST_UPDATED_BY", type: "VARCHAR2(11 BYTE)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-92") {
		createTable(tableName: "ROAD_SECTION") {
			column(name: "ID", type: "NUMBER(19,0)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "SYS_C004319", primaryKeyTablespace: "USERS")
			}

			column(name: "VERSION", type: "NUMBER(19,0)") {
				constraints(nullable: "false")
			}

			column(name: "DESCRIPTION", type: "VARCHAR2(400 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "FACILITY_ID", type: "NUMBER(19,0)") {
				constraints(nullable: "false")
			}

			column(name: "START_STATION", type: "NUMBER(8,2)") {
				constraints(nullable: "false")
			}

			column(name: "END_STATION", type: "NUMBER(8,2)") {
				constraints(nullable: "false")
			}

			column(name: "DATE_CREATED", type: "TIMESTAMP(6)") {
				constraints(nullable: "false")
			}

			column(name: "LAST_UPDATED", type: "TIMESTAMP(6)") {
				constraints(nullable: "false")
			}

			column(name: "ORIENTATION", type: "VARCHAR2(25 BYTE)")

			column(name: "CREATED_BY", type: "VARCHAR2(11 BYTE)")

			column(name: "LAST_UPDATED_BY", type: "VARCHAR2(11 BYTE)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-93") {
		createTable(tableName: "SENSOR") {
			column(name: "SEQ", type: "NUMBER(3,0)") {
				constraints(nullable: "false")
			}

			column(name: "DESCRIPTION", type: "VARCHAR2(255 BYTE)")

			column(name: "DATE_INSTALLED", type: "DATE")

			column(name: "DATE_REMOVED", type: "DATE")

			column(name: "CABINET", type: "VARCHAR2(4 BYTE)")

			column(name: "STATION_FT", type: "NUMBER(16,8)")

			column(name: "OFFSET_FT", type: "NUMBER(7,2)")

			column(name: "SENSOR_DEPTH_IN", type: "NUMBER(9,3)")

			column(name: "ORIENTATION", type: "VARCHAR2(13 BYTE)")

			column(name: "COLLECTION_TYPE", type: "VARCHAR2(8 BYTE)")

			column(name: "DYNAMIC_STATIC", type: "CHAR(1)")

			column(name: "LAST_UPDATED", type: "TIMESTAMP(6)")

			column(name: "DATE_CREATED", type: "TIMESTAMP(6)")

			column(name: "SENSOR_MODEL_ID", type: "NUMBER(19,0)")

			column(name: "ID", type: "NUMBER(19,0)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "SYS_C004324", primaryKeyTablespace: "USERS")
			}

			column(name: "VERSION", type: "NUMBER(19,0)") {
				constraints(nullable: "false")
			}

			column(name: "SENSOR_ID", type: "VARCHAR2(20 BYTE)")

			column(name: "CURRENT_STATUS", type: "VARCHAR2(20 BYTE)")

			column(name: "LAYER_ID", type: "NUMBER(19,0)")

			column(name: "CREATED_BY", type: "VARCHAR2(11 BYTE)")

			column(name: "LAST_UPDATED_BY", type: "VARCHAR2(11 BYTE)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-94") {
		createTable(tableName: "SENSOR_EVALUATION") {
			column(name: "SENSOR_ID", type: "NUMBER(19,0)") {
				constraints(nullable: "false")
			}

			column(name: "DATE_COMMENT", type: "DATE") {
				constraints(nullable: "false")
			}

			column(name: "COMMENT_BY", type: "VARCHAR2(50 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "EVALUATION_METHOD", type: "VARCHAR2(20 BYTE)") {
				constraints(nullable: "false")
			}

			column(name: "RESULT", type: "VARCHAR2(20 BYTE)")

			column(name: "REASON", type: "VARCHAR2(200 BYTE)")

			column(name: "LAST_UPDATED", type: "TIMESTAMP(6)")

			column(name: "DATE_CREATED", type: "TIMESTAMP(6)")

			column(name: "ID", type: "NUMBER(19,0)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "SYS_C004332", primaryKeyTablespace: "USERS")
			}

			column(name: "VERSION", type: "NUMBER(19,0)") {
				constraints(nullable: "false")
			}

			column(name: "CREATED_BY", type: "VARCHAR2(11 BYTE)")

			column(name: "LAST_UPDATED_BY", type: "VARCHAR2(11 BYTE)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-95") {
		createTable(tableName: "SENSOR_MODEL") {
			column(name: "MODEL", type: "VARCHAR2(3 BYTE)")

			column(name: "DESCRIPTION", type: "VARCHAR2(200 BYTE)")

			column(name: "COMMENTS", type: "VARCHAR2(2000 BYTE)")

			column(name: "MEASUREMENT_TYPE", type: "VARCHAR2(40 BYTE)")

			column(name: "DATA_VALUES_TABLE", type: "VARCHAR2(40 BYTE)")

			column(name: "MODEL_NUM", type: "VARCHAR2(40 BYTE)")

			column(name: "MEASUREMENT_UNITS", type: "VARCHAR2(30 BYTE)")

			column(name: "MIN_POSSIBLE_VALUE", type: "NUMBER(15,5)")

			column(name: "MAX_POSSIBLE_VALUE", type: "NUMBER(15,5)")

			column(name: "MIN_EXPECTED_VALUE", type: "NUMBER(15,5)")

			column(name: "MAX_EXPECTED_VALUE", type: "NUMBER(15,5)")

			column(name: "ID", type: "NUMBER(19,0)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "SYS_C004335", primaryKeyTablespace: "USERS")
			}

			column(name: "VERSION", type: "NUMBER(19,0)") {
				constraints(nullable: "false")
			}

			column(name: "DYNAMIC_STATIC", type: "VARCHAR2(1 BYTE)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-96") {
		createTable(tableName: "TRANSVERSE_JOINT") {
			column(name: "LAYER_ID", type: "NUMBER(22,0)")

			column(name: "STATION", type: "NUMBER(12,2)")

			column(name: "JOINT_NUMBER", type: "NUMBER(5,0)")

			column(name: "SEALANT_TYPE", type: "CHAR(20)")

			column(name: "ID", type: "NUMBER(19,0)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "SYS_C004339", primaryKeyTablespace: "USERS")
			}

			column(name: "VERSION", type: "NUMBER(22,0)") {
				constraints(nullable: "false")
			}

			column(name: "LAST_UPDATED", type: "TIMESTAMP(6)")

			column(name: "DATE_CREATED", type: "TIMESTAMP(6)")

			column(name: "CREATED_BY", type: "VARCHAR2(10 BYTE)")

			column(name: "LAST_UPDATED_BY", type: "VARCHAR2(10 BYTE)")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-97") {
		createIndex(indexName: "DISTRESS_JPCC_INDEX", tableName: "DISTRESS_JPCC", tablespace: "USERS", unique: "true") {
			column(name: "DAY")

			column(name: "CELL")

			column(name: "LANE")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-98") {
		createIndex(indexName: "DISTRESS_OBSI_DATA_UIDX", tableName: "DISTRESS_OBSI_DATA", tablespace: "USERS", unique: "true") {
			column(name: "CELL")

			column(name: "LANE")

			column(name: "DAY")

			column(name: "TRIAL")

			column(name: "TIME")

			column(name: "FREQ_HZ")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-99") {
		createIndex(indexName: "DISTRESS_OBSI_SUMMARY_UIDX", tableName: "DISTRESS_OBSI_SUMMARY", tablespace: "USERS", unique: "true") {
			column(name: "CELL")

			column(name: "LANE")

			column(name: "DAY")

			column(name: "TIME")

			column(name: "TRIAL")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-100") {
		createIndex(indexName: "DPF_UIDX", tableName: "DISTRESS_PCC_FAULTS", tablespace: "USERS", unique: "true") {
			column(name: "DAY")

			column(name: "CELL")

			column(name: "LANE")

			column(name: "SAMPLE_TIME")

			column(name: "JOINT_NUMBER")

			column(name: "OFFSET_FROM_CENTERLINE_FT")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-101") {
		createIndex(indexName: "DSA_UIDX", tableName: "DISTRESS_SOUND_ABSORPTION", tablespace: "USERS", unique: "true") {
			column(name: "DAY")

			column(name: "CELL")

			column(name: "STATION")

			column(name: "OFFSET_FT")

			column(name: "LANE")

			column(name: "WHEELPATH")

			column(name: "TRIAL")

			column(name: "FREQUENCY_HZ")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-102") {
		createIndex(indexName: "JSEC_PERM_TYPE", tableName: "JSEC_PERMISSION", tablespace: "USERS", unique: "true") {
			column(name: "TYPE")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-103") {
		createIndex(indexName: "JSEC_ROLE_NAME", tableName: "JSEC_ROLE", tablespace: "USERS", unique: "true") {
			column(name: "NAME")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-104") {
		createIndex(indexName: "MAT_CONC_AIR_VOID_RES_IDX", tableName: "MAT_CONC_AIR_VOID_RESULTS", tablespace: "USERS", unique: "true") {
			column(name: "MNROAD_ID")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-105") {
		createIndex(indexName: "MAT_CONC_FIELD_RES_IDX", tableName: "MAT_CONC_FIELD_RESULTS", tablespace: "USERS", unique: "true") {
			column(name: "MNROAD_ID")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-106") {
		createIndex(indexName: "MAT_CONC_FREEZE_THAW_RES_IDX", tableName: "MAT_CONC_FREEZE_THAW_RESULTS", tablespace: "USERS", unique: "true") {
			column(name: "MNROAD_ID")

			column(name: "NUMBER_OF_CYCLES")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-107") {
		createIndex(indexName: "MAT_CONC_STRENGTH_RES_IDX", tableName: "MAT_CONC_STRENGTH_RESULTS", tablespace: "USERS", unique: "true") {
			column(name: "MNROAD_ID")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-108") {
		createIndex(indexName: "MATPROCTCURV_UIDX", tableName: "MAT_PROCTOR_CURVES", tablespace: "USERS", unique: "true") {
			column(name: "PROCTOR_CURVE")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-109") {
		createIndex(indexName: "MAT_SOIL_TESTS_IDX", tableName: "MAT_SOIL_TESTS", tablespace: "USERS", unique: "true") {
			column(name: "MNROAD_ID")

			column(name: "LAB_SAMPLE_ID")

			column(name: "TEST_TYPE")

			column(name: "SUBTEST_NAME")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-110") {
		createIndex(indexName: "MAT_UNBOUND_GRADATAIONS_UIDX", tableName: "MAT_UNBOUND_GRADATIONS", tablespace: "USERS", unique: "true") {
			column(name: "MNROAD_ID")

			column(name: "LAB_SAMPLE_ID")
		}
	}

	changeSet(author: "den (generated)", id: "1321036066529-111") {
		addForeignKeyConstraint(baseColumnNames: "ROAD_SECTION_ID", baseTableName: "CELL", baseTableSchemaName: "MNR", constraintName: "ROAD_SECTION_FK", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "ROAD_SECTION", referencedTableSchemaName: "MNR", referencesUniqueColumn: "false")
	}

	changeSet(author: "den (generated)", id: "1321036066529-112") {
		addForeignKeyConstraint(baseColumnNames: "CELL_COVERED_BY_ID", baseTableName: "CELL_CELL", baseTableSchemaName: "MNR", constraintName: "FK_CELL_COVERED_BY", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "CELL", referencedTableSchemaName: "MNR", referencesUniqueColumn: "false")
	}

	changeSet(author: "den (generated)", id: "1321036066529-113") {
		addForeignKeyConstraint(baseColumnNames: "CELL_COVERS_ID", baseTableName: "CELL_CELL", baseTableSchemaName: "MNR", constraintName: "FK_CELL_COVERS", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "CELL", referencedTableSchemaName: "MNR", referencesUniqueColumn: "false")
	}

	changeSet(author: "den (generated)", id: "1321036066529-114") {
		addForeignKeyConstraint(baseColumnNames: "CELL_ID", baseTableName: "CELL_CELL", baseTableSchemaName: "MNR", constraintName: "FK_CELL_CELL", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "CELL", referencedTableSchemaName: "MNR", referencesUniqueColumn: "false")
	}

	changeSet(author: "den (generated)", id: "1321036066529-115") {
		addForeignKeyConstraint(baseColumnNames: "PERMISSION_ID", baseTableName: "JSEC_ROLE_PERMISSION_REL", baseTableSchemaName: "MNR", constraintName: "FK6DF5807AEB28BDB0", deferrable: "false", initiallyDeferred: "false", onDelete: "CASCADE", referencedColumnNames: "ID", referencedTableName: "JSEC_PERMISSION", referencedTableSchemaName: "MNR", referencesUniqueColumn: "false")
	}

	changeSet(author: "den (generated)", id: "1321036066529-116") {
		addForeignKeyConstraint(baseColumnNames: "ROLE_ID", baseTableName: "JSEC_ROLE_PERMISSION_REL", baseTableSchemaName: "MNR", constraintName: "FK6DF5807AE3CC60D0", deferrable: "false", initiallyDeferred: "false", onDelete: "CASCADE", referencedColumnNames: "ID", referencedTableName: "JSEC_ROLE", referencedTableSchemaName: "MNR", referencesUniqueColumn: "false")
	}

	changeSet(author: "den (generated)", id: "1321036066529-117") {
		addForeignKeyConstraint(baseColumnNames: "PERMISSION_ID", baseTableName: "JSEC_USER_PERMISSION_REL", baseTableSchemaName: "MNR", constraintName: "FK28FF6085EB28BDB0", deferrable: "false", initiallyDeferred: "false", onDelete: "CASCADE", referencedColumnNames: "ID", referencedTableName: "JSEC_PERMISSION", referencedTableSchemaName: "MNR", referencesUniqueColumn: "false")
	}

	changeSet(author: "den (generated)", id: "1321036066529-118") {
		addForeignKeyConstraint(baseColumnNames: "USER_ID", baseTableName: "JSEC_USER_PERMISSION_REL", baseTableSchemaName: "MNR", constraintName: "FK28FF608588F724B0", deferrable: "false", initiallyDeferred: "false", onDelete: "CASCADE", referencedColumnNames: "ID", referencedTableName: "JSEC_USER", referencedTableSchemaName: "MNR", referencesUniqueColumn: "false")
	}

	changeSet(author: "den (generated)", id: "1321036066529-119") {
		addForeignKeyConstraint(baseColumnNames: "ROLE_ID", baseTableName: "JSEC_USER_ROLE_REL", baseTableSchemaName: "MNR", constraintName: "FK238F64ACE3CC60D0", deferrable: "false", initiallyDeferred: "false", onDelete: "CASCADE", referencedColumnNames: "ID", referencedTableName: "JSEC_ROLE", referencedTableSchemaName: "MNR", referencesUniqueColumn: "false")
	}

	changeSet(author: "den (generated)", id: "1321036066529-120") {
		addForeignKeyConstraint(baseColumnNames: "USER_ID", baseTableName: "JSEC_USER_ROLE_REL", baseTableSchemaName: "MNR", constraintName: "FK238F64AC88F724B0", deferrable: "false", initiallyDeferred: "false", onDelete: "CASCADE", referencedColumnNames: "ID", referencedTableName: "JSEC_USER", referencedTableSchemaName: "MNR", referencesUniqueColumn: "false")
	}

	changeSet(author: "den (generated)", id: "1321036066529-121") {
		addForeignKeyConstraint(baseColumnNames: "CELL_ID", baseTableName: "LANE", baseTableSchemaName: "MNR", constraintName: "CELL_FK", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "CELL", referencedTableSchemaName: "MNR", referencesUniqueColumn: "false")
	}

	changeSet(author: "den (generated)", id: "1321036066529-122") {
		addForeignKeyConstraint(baseColumnNames: "LANE_ID", baseTableName: "LAYER", baseTableSchemaName: "MNR", constraintName: "LANE_FK", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "LANE", referencedTableSchemaName: "MNR", referencesUniqueColumn: "false")
	}

	changeSet(author: "den (generated)", id: "1321036066529-123") {
		addForeignKeyConstraint(baseColumnNames: "MATERIAL_ID", baseTableName: "LAYER", baseTableSchemaName: "MNR", constraintName: "FK61FD5517CF7707A", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "MATERIAL", referencedTableSchemaName: "MNR", referencesUniqueColumn: "false")
	}

	changeSet(author: "den (generated)", id: "1321036066529-124") {
		addForeignKeyConstraint(baseColumnNames: "FACILITY_ID", baseTableName: "ROAD_SECTION", baseTableSchemaName: "MNR", constraintName: "FACILITY_FK", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "FACILITY", referencedTableSchemaName: "MNR", referencesUniqueColumn: "false")
	}

	changeSet(author: "den (generated)", id: "1321036066529-125") {
		addForeignKeyConstraint(baseColumnNames: "SENSOR_ID", baseTableName: "SENSOR_EVALUATION", baseTableSchemaName: "MNR", constraintName: "SENSOR_EVAL", deferrable: "false", initiallyDeferred: "false", onDelete: "CASCADE", referencedColumnNames: "ID", referencedTableName: "SENSOR", referencedTableSchemaName: "MNR", referencesUniqueColumn: "false")
	}
}
