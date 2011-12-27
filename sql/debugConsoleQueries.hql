import us.mn.state.dot.mnroad.Sensor
import us.mn.state.dot.mnroad.Layer
import us.mn.state.dot.mnroad.Lane
import us.mn.state.dot.mnroad.Cell
import us.mn.state.dot.mnroad.Facility
/*
def f = Facility.createCriteria()
def fl = f.list() {
   like("description", "%test%")
}
//
def f = Cell.createCriteria()
def fl = f.list() {
   like("name", "%SemMaterials%")
}
//  
def f = Cell.createCriteria()
def fl = f.list() {
   projections {
      groupProperty("name")
      rowCount()
   }
   roadSection { }
}
//
def f = Cell.createCriteria()
def fl = f.list() {
   roadSection {
      projections {
         like("description", "%Pole%")
      }
   }
}
//
def f = Sensor.createCriteria()
def fl = f.list() {
   layer {
         lane {
            cell {
            projections {
               like("name", "%SemMaterials%")
            }
         }
      }
   }
}
//
def f = Sensor.createCriteria()
def fl = f.list() {
            projections {
               isNull("layer")
            }
}
if (fl && fl.size()>0) {
   fl.each {
      println "${it.sensorModel.model}-${it.seq}, D:${it.sensorDepthIn}"
   }
}
//
*/
def f = Sensor.createCriteria()
def fl = f.list() {
            projections {
               isNull("layer")
            }
}
if (fl && fl.size()>0) {
   fl.each {
      println "${it.sensorModel.model}-${it.seq}, D:${it.sensorDepthIn}"
   }
}
//
def f = Sensor.createCriteria()
def fl = f.list() {
            projections {
               and {
                  isNotNull("layer")
                  eq ("description", "Panel 1 - Center Shoulder ")
               }
            }
}
if (fl && fl.size()>0) {
   fl.each {
      println "${it.sensorModel.model}-${it.seq}, D:${it.sensorDepthIn}"
   }
}
//
def f = Sensor.createCriteria()
def fl = f.list() {
      and {
         projections {
            isNotNull("layer")
         }
         layer {
            projections {
               eq("layerNum", 3)
            }
         }
      }
}
if (fl && fl.size()>0) {
         println "${fl.size()} items returned."
   fl.each {
      println "${it.sensorModel.model}-${it.seq}, D:${it.sensorDepthIn}, ${it.layer.material}"
   }
}
//
def f = Sensor.createCriteria()
def fl = f.list() {
      and {
         projections {
            isNotNull("layer")
         }
         layer {
            and {
               projections {
                  eq("layerNum", 3)
               }
               lane {
                  and {
                     projections {
                        eq("name", "Driving")
                     }
                     cell {
                        projections {
                           eq("cellNumber", 2)
                        }
                     }
                  }
               }  
            }
         }
      }
}
if (fl && fl.size()>0) {
         println "${fl.size()} items returned."
   fl.each {
      println "${it.sensorModel.model}-${it.seq}, D:${it.sensorDepthIn}, ${it.layer.material}"
   }
}
//
import us.mn.state.dot.mnroad.Sensor
import us.mn.state.dot.mnroad.Layer
import us.mn.state.dot.mnroad.Lane
import us.mn.state.dot.mnroad.Cell

def f = Sensor.createCriteria()
def fl = f.list() {
      and {
         projections {
            isNotNull("layer")
         }
         layer {
            and {
               projections {
                  isNotNull("lane")
               }
               lane {
                  and {
                     projections {
                        eq("name", "Driving")
                     }
                     cell {
                        projections {
                           eq("cellNumber", 2)
                        }
                     }
                  }
               }  
            }
         }
      }
}
if (fl && fl.size()>0) {
         println "${fl.size()} items returned."
   fl.each {
      println "${it.sensorModel.model}-${it.seq}, D:${it.sensorDepthIn}, ${it.layer.material}"
   }
}
