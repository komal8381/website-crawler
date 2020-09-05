/**
  * ASN class
  */
case class ASN(country:String,name:String,registry:String,num_ip_addresses:Int){
  override def toString(): String ={
    country + "," + name + "," + registry + "," + num_ip_addresses
  }
}
