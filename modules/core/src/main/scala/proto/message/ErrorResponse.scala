package skunk
package proto
package message

import scodec.Decoder
import scodec.codecs._

final case class ErrorResponse(info: Map[Char, String]) extends BackendMessage

object ErrorResponse {

  // ErrorResponse (B)
  val Tag = 'E'

  // The message body consists of one or more identified fields, followed by a zero byte as a
  // terminator. Fields can appear in any order. For each field there is the following:
  //
  // Byte1  - A code identifying the field type; if zero, this is the message terminator and no
  //          string follows. The presently defined field types are listed in Section 48.6. Since
  //          more field types might be added in future, frontends should silently ignore fields of
  //          unrecognized type.
  // String - The field value.
  val decoder: Decoder[BackendMessage] =
    list(cstring).map { ss =>
      val kv = ss.init.map(s => s.head -> s.tail).toMap // last one is always empty
      ErrorResponse(kv)
    }

}