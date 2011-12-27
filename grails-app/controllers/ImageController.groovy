class ImageController {
  def defaultAction ='show'

 def show= {
     //loads the class with a name and assigns obj a new instance created of the same object
     def obj = Class.forName("${params.classname}",true,Thread.currentThread().contextClassLoader).newInstance();
     def object = obj.get( params.id )
     response.setContentType(params.mime)
     byte[] image = object."${params.fieldName}"
     response.outputStream << image
   }

}
