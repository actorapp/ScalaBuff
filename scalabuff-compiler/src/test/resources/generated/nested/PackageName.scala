// Generated by ScalaBuff, the Scala Protocol Buffers compiler. DO NOT EDIT!
// source: package_name.proto

package resources.generated.nested

final case class PackageTest (
	requiredField: Int = 0
) extends com.google.protobuf.GeneratedMessageLite
	with com.google.protobuf.MessageLite.Builder
	with net.sandrogrzicic.scalabuff.Message[PackageTest]
	with net.sandrogrzicic.scalabuff.Parser[PackageTest] {



	def writeTo(output: com.google.protobuf.CodedOutputStream) {
		output.writeInt32(1, requiredField)
	}

	def getSerializedSize = {
		import com.google.protobuf.CodedOutputStream._
		var __size = 0
		__size += computeInt32Size(1, requiredField)

		__size
	}

	def mergeFrom(in: com.google.protobuf.CodedInputStream, extensionRegistry: com.google.protobuf.ExtensionRegistryLite): PackageTest = {
		import com.google.protobuf.ExtensionRegistryLite.{getEmptyRegistry => _emptyRegistry}
		var __requiredField: Int = 0

		def __newMerged = PackageTest(
			__requiredField
		)
		while (true) in.readTag match {
			case 0 => return __newMerged
			case 8 => __requiredField = in.readInt32()
			case default => if (!in.skipField(default)) return __newMerged
		}
		null
	}

	def mergeFrom(m: PackageTest) = {
		PackageTest(
			m.requiredField
		)
	}

	def getDefaultInstanceForType = PackageTest.defaultInstance
	def clear = getDefaultInstanceForType
	def isInitialized = true
	def build = this
	def buildPartial = this
	def parsePartialFrom(cis: com.google.protobuf.CodedInputStream, er: com.google.protobuf.ExtensionRegistryLite) = mergeFrom(cis, er)
	override def getParserForType = this
	def newBuilderForType = getDefaultInstanceForType
	def toBuilder = this
	def toJson(indent: Int = 0): String = "ScalaBuff JSON generation not enabled. Use --generate_json_method to enable."
}

object PackageTest {
	@beans.BeanProperty val defaultInstance = new PackageTest()

	def parseFrom(data: Array[Byte]): PackageTest = defaultInstance.mergeFrom(data)
	def parseFrom(data: Array[Byte], offset: Int, length: Int): PackageTest = defaultInstance.mergeFrom(data, offset, length)
	def parseFrom(byteString: com.google.protobuf.ByteString): PackageTest = defaultInstance.mergeFrom(byteString)
	def parseFrom(stream: java.io.InputStream): PackageTest = defaultInstance.mergeFrom(stream)
	def parseDelimitedFrom(stream: java.io.InputStream): Option[PackageTest] = defaultInstance.mergeDelimitedFromStream(stream)

	val REQUIRED_FIELD_FIELD_NUMBER = 1

	def newBuilder = defaultInstance.newBuilderForType
	def newBuilder(prototype: PackageTest) = defaultInstance.mergeFrom(prototype)

}

object PackageName {
	def registerAllExtensions(registry: com.google.protobuf.ExtensionRegistryLite) {
	}

	private val fromBinaryHintMap = collection.immutable.HashMap[String, Array[Byte] ⇒ com.google.protobuf.GeneratedMessageLite](
		 "PackageTest" -> (bytes ⇒ PackageTest.parseFrom(bytes))
	)

	def deserializePayload(payload: Array[Byte], payloadType: String): com.google.protobuf.GeneratedMessageLite = {
		fromBinaryHintMap.get(payloadType) match {
			case Some(f) ⇒ f(payload)
			case None    ⇒ throw new IllegalArgumentException(s"unimplemented deserialization of message payload of type [${payloadType}]")
		}
	}
}
