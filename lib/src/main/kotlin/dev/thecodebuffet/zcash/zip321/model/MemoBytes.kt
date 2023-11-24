import java.util.Base64

class MemoBytes {
    companion object {
        const val maxLength: Int = 512
    }

    val data: ByteArray
    sealed class MemoError(message: String) : RuntimeException(message) {
        object MemoTooLong : MemoError("MemoBytes exceeds max length of 512 bytes")
        object MemoEmpty : MemoError("MemoBytes can't be initialized with empty bytes")
    }

    @Throws(MemoError::class)
    constructor(data: ByteArray) {
        require(data.isNotEmpty()) { throw MemoError.MemoEmpty }
        require(data.size <= maxLength) { throw MemoError.MemoTooLong }

        this.data = data
    }

    @Throws(MemoError::class)
    constructor(string: String) {
        require(string.isNotEmpty()) { throw MemoError.MemoEmpty }
        require(string.length <= maxLength) { throw MemoError.MemoTooLong }

        this.data = string.encodeToByteArray()
    }

    fun toBase64URL(): String {
        return Base64.getUrlEncoder().encodeToString(data)
            .replace("/", "_")
            .replace("+", "-")
            .replace("=", "")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MemoBytes

        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }
}
