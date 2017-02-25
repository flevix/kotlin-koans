package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int = when {
        year != other.year -> year - other.year
        month != other.month -> month - other.month
        else -> dayOfMonth - other.dayOfMonth
    }
}

data class RepeatedTimeInterval(val interval: TimeInterval, val number: Int)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

operator fun MyDate.plus(interval: TimeInterval): MyDate = addTimeIntervals(interval, 1)

operator fun MyDate.plus(repeated: RepeatedTimeInterval): MyDate = addTimeIntervals(repeated.interval, repeated.number)

operator fun TimeInterval.times(number: Int): RepeatedTimeInterval = RepeatedTimeInterval(this, number)

class DateRange(override val start: MyDate, override val endInclusive: MyDate) : ClosedRange<MyDate>, Iterable<MyDate> {

    override operator fun iterator(): Iterator<MyDate> = object : Iterator<MyDate> {
        var next = start

        override operator fun hasNext(): Boolean = next <= endInclusive

        override operator fun next(): MyDate {
            val current = next;
            next = next.nextDay();
            return current;
        }
    }

}
