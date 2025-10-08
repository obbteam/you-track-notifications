import java.text.SimpleDateFormat
import java.util.Date

fun convertEpochToLocal(epoch: Long): String = SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(Date(epoch))

