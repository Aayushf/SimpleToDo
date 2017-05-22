package com.simpletodo.aayushf.simpletodo

import java.util.Date

/**
 * Created by ayfadia on 4/17/17.
 */

class Task {
    internal var task: String
    internal var points: Int = 0


    internal var dateadded: Date
    internal var datepending: Date
    internal var category: String
    internal var primk: Int = 0
    internal var done: Boolean = false
    internal var colour: Int = 0

    constructor(task: String, points: Int, category: String, colour: Int, done: Boolean, dateadded: Date, datepending: Date) {
        this.task = task
        this.points = points
        this.category = category
        this.done = done
        this.colour = colour
        this.dateadded = dateadded
        this.datepending = datepending
    }

    constructor(task: String, points: Int, category: String, primk: Int, colour: Int, done: Boolean, dateadded: Date, datepending: Date) {
        this.dateadded = dateadded
        this.datepending = datepending
        this.task = task
        this.points = points
        this.category = category
        this.primk = primk
        this.done = done
        this.colour = colour
    }

    companion object {
        val coloursnames = arrayOf("White", "Tomato", "Tangerine", "Banana", "Basil", "Sage", "Peacock", "Blueberry", "Lavender", "Grape", "Flamingo", "Graphite")
        val quotes = arrayOf("It's Not Over Until You Succeed.", "The Fact That You Are Not Where You Want To Be Should Be Enough Inspiration.")
        var colours = intArrayOf(0xFF000000.toInt(), 0xFFD50000.toInt(), 0xFFF4511E.toInt(), 0xFFF6BF26.toInt(), 0xFF0B8043.toInt(), 0xFF33B679.toInt(), 0xFF039BE5.toInt(), 0xFF3F51B5.toInt(), 0xFF7986CB.toInt(), 0xFF8E24AA.toInt(), 0xFFE67C73.toInt(), 0xFF616161.toInt())
    }


}
