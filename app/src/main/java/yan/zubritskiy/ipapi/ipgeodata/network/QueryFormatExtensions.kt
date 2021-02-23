package yan.zubritskiy.ipapi.ipgeodata.network

fun List<String>.commaQueryFormat(): String {
    val builder = StringBuilder()
    forEachIndexed { index, s ->
        if (index == 0) builder.append(s)
        else builder.append(",$s")
    }
    return builder.toString()
}