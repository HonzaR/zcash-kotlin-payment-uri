package dev.thecodebuffet.zcash.zip321.extensions

fun String.qcharEncoded(): String? {
    val qcharEncodeAllowed = setOf('-', '.', '_', '~', '!', '$', '\'', '(', ')', '*', '+', ',', ';', '@', ':').map { it -> it.toString() }
    return this.replace(Regex("[^A-Za-z0-9\\-._~!$'()*+,;@:]")) { matched ->
        if (matched.value in qcharEncodeAllowed) matched.value else "%" + matched.value.toCharArray().joinToString("%") { byte -> "%02x".format(byte.code.toByte()) }
    }
}