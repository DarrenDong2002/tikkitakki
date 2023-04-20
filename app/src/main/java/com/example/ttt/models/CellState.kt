package com.example.ttt.models
import  androidx.annotation.DrawableRes
import  com.example.ttt.R

sealed class CellState(@DrawableRes val res: Int) {
    object Star : CellState(R.drawable.ic_star)
    object Circle : CellState(R.drawable.ic_circle)
    object Empty : CellState(R.drawable.ic_empty)

}