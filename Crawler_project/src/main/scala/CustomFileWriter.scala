import java.io.{BufferedWriter, File, FileWriter}

class CustomFileWriter {
  def writeToFile(filePath: String, data:String ): Unit ={
    val file = new File(filePath)
    if (!file.exists()) {
      new File(file.getParent()).mkdirs()
      file.createNewFile()
    }
    else {
      file.delete()
    }
    val bw = new BufferedWriter(new FileWriter(file))
   bw.write(data);

    bw.close()
  }

}
