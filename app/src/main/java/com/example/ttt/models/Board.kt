package com.example.ttt.models

class Board(private val gameBoard: MutableMap<Cell, CellState> = mutableMapOf()) {
    fun getState(cell: Cell): CellState {
        return gameBoard[cell] ?: CellState.Empty
    }
    /** Update cells  **/
    fun setCell(cell: Cell, state: CellState): Boolean {
        if(gameBoard.containsKey(cell)){
            return false
        }
        gameBoard[cell] = state
        return true

    }

    /** Reset Map **/
    fun resetBoard(){
        gameBoard.clear()

    }

    /** determines bots movements **/
    fun findNextWinningMove(state: CellState): Cell? = when{
        Cell.TOP_LEFT wins state -> Cell.TOP_LEFT
        Cell.TOP_CENTER wins state -> Cell.TOP_CENTER
        Cell.TOP_RIGHT wins state -> Cell.TOP_RIGHT
        Cell.CENTER_LEFT wins state -> Cell.CENTER_LEFT
        Cell.CENTER_CENTER wins state -> Cell.CENTER_CENTER
        Cell.CENTER_RIGHT wins state -> Cell.CENTER_RIGHT
        Cell.BOTTOM_LEFT wins state -> Cell.BOTTOM_LEFT
        Cell.BOTTOM_CENTER wins state -> Cell.BOTTOM_CENTER
        Cell.BOTTOM_RIGHT wins state -> Cell.BOTTOM_RIGHT
        else -> null
    }
    private infix fun Cell.wins(state: CellState): Boolean{
        if (gameBoard.containsKey(this)){
            return false
        }
        gameBoard[this] =state
        val hasWon = hasStateWon(state)
        gameBoard.remove(this)
        return hasWon
    }

    val boardState: BoardState
    get() = when{
        // Winning state for users (X)
        hasStateWon(CellState.Star) -> {
            BoardState.STARS_WIN

        }
        // Winning state for bot (O)
        hasStateWon(CellState.Circle) -> {
            resetBoard()
            BoardState.CIRCLES_WIN
        }
        // Incomplete game
        gameBoard.size < 9 -> BoardState.INCOMPLETE
        else -> BoardState.DRAW
    }

    private fun hasStateWon(state: CellState): Boolean {
        fun testState(vararg cells:Cell): Boolean = cells.all { cell -> gameBoard[cell] == state
        }
        //checking win conditions
        return testState(Cell.TOP_LEFT, Cell.CENTER_LEFT, Cell.BOTTOM_LEFT) ||          //left vertical
                testState(Cell.TOP_CENTER, Cell.CENTER_CENTER, Cell.BOTTOM_CENTER) ||   // middle vertical
                testState(Cell.TOP_RIGHT, Cell.CENTER_RIGHT, Cell.BOTTOM_RIGHT) ||      // right vertical
                testState(Cell.TOP_LEFT, Cell.TOP_CENTER, Cell.TOP_RIGHT) ||  // top horizontal
                testState(Cell.CENTER_LEFT, Cell.CENTER_CENTER, Cell.CENTER_RIGHT) ||  // middle horizontal
                testState(Cell.BOTTOM_LEFT, Cell.BOTTOM_CENTER, Cell.BOTTOM_RIGHT) ||  // bottom horizontal
                testState(Cell.TOP_LEFT, Cell.CENTER_CENTER, Cell. BOTTOM_RIGHT) ||  // left to right digonal (\)
                testState(Cell. TOP_RIGHT, Cell.CENTER_CENTER, Cell.BOTTOM_LEFT)     // right to left diagonal (/)
    }
}