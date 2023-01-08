//******************Задание 1******************
enum class Time{
    SECONDS, MINUTES, HOURS, DAYS
}

fun changeOfEndings(numberSeconds:Int = 0, time:Time):String {
    return when (time) {
        Time.SECONDS -> when {
            numberSeconds % 10 == 1 && numberSeconds % 100 != 11 -> "секунда"
            numberSeconds % 10 in 2..4 -> "секунды"
            else -> "секунд назад"
        }
        Time.MINUTES -> when {
            secondIn(numberSeconds) % 10 == 1 && secondIn(numberSeconds) % 100 != 11 -> "минуту"
            secondIn(numberSeconds) % 10 in 2..4 -> "минуты"
            else -> "минут"
        }
        Time.HOURS -> when {
            secondIn(numberSeconds) % 10 == 1 && secondIn(numberSeconds) % 100 != 11 -> "час"
            secondIn(numberSeconds) % 10 in 2..4 -> "часа"
            else -> "часов назад"
        }
        Time.DAYS -> when {
            secondIn(numberSeconds) % 10 == 1 && secondIn(numberSeconds) % 100 == 11 -> "сутки"
            else -> "суток"
        }
    }
}

fun secondIn(numberSeconds:Int = 0):Int{
    return when(numberSeconds){
        in 0..60 -> numberSeconds
        in 61 .. 3600 -> numberSeconds/60
        in 3601 .. 86400 -> numberSeconds/3600
        else -> numberSeconds/86400
    }
}

fun agoToText(name:String, numberSeconds:Int): String {
    return when(numberSeconds){
        in 0..60 -> "$name был(а) в сети только что"
        in 61 .. 3600 -> "$name был(а) в сети ${secondIn(numberSeconds)} ${changeOfEndings(numberSeconds, Time.MINUTES)} назад"
        in 3601 .. 86400 -> "$name был(а) в сети ${secondIn(numberSeconds)} ${changeOfEndings(numberSeconds, Time.HOURS)} назад"
        in 86401 .. 172800 -> "$name был(а) в сети вчера"
        in 172801 .. 259200 -> "$name был(а) в сети позавчера"
        else -> "$name был(а) в сети давно"
    }
}

//******************Задание 2******************
enum class KindPay{
    MASTERCARD, MAESTRO, VKPAY, VISA, MIR
}

fun takeTransferFee(amount: Double,
                    previousTransfer: Double,
                    kindPayTransfer: KindPay = KindPay.VKPAY,
                    ): String {
    val percentTransfer: Double
    val minTransfer: Double
    val maxTransfer: Double
    val aboveTransfer: Double
    var transferFee: Double

    when (kindPayTransfer) {
            KindPay.VKPAY -> {
                percentTransfer = 0.00;
                minTransfer = 0.00;
                maxTransfer = 0.00;
                aboveTransfer = 0.00}
            KindPay.MAESTRO, KindPay.MASTERCARD -> {
                percentTransfer = 0.006;
                minTransfer = 0.00;
                maxTransfer = 75000.00
                aboveTransfer = 20.00 }
            KindPay.VISA, KindPay.MIR -> {
                percentTransfer = 0.0075;
                minTransfer = 35.00;
                maxTransfer = 0.00
                aboveTransfer = 0.00 }
        }

    transferFee = amount * percentTransfer + aboveTransfer

    if (kindPayTransfer == KindPay.MIR || kindPayTransfer == KindPay.VISA) {
        if(transferFee < minTransfer){
            transferFee = minTransfer
        }
    }

    if (kindPayTransfer == KindPay.MAESTRO || kindPayTransfer == KindPay.MASTERCARD) {
        if(previousTransfer < maxTransfer){
            transferFee = 0.00
        }
    }

    var transferAmount = amount - transferFee
    return "Ваша комиссия за перевод: $transferFee сумма перевода $transferAmount"
}

fun main(){
    print("Задание 1\n")
    print(agoToText("Alex",121))
    print("\nЗадание 2\n")
    print(takeTransferFee(100.00,  74000.00, KindPay.VISA))
}
