package com.x05nelsonm.matricescalculatorchallenge

data class TwoByTwoMatrix(var matrix: Array<Array<Double>>) {
    operator fun times(b: TwoByTwoMatrix): Array<Array<Double>> {
        val a11 = matrix[0][0]
        val a12 = matrix[0][1]
        val a21 = matrix[1][0]
        val a22 = matrix[1][1]
        val b11 = b.matrix[0][0]
        val b12 = b.matrix[0][1]
        val b21 = b.matrix[1][0]
        val b22 = b.matrix[1][1]

        val tempMatrix = TwoByTwoMatrix(Array(2) { Array(2) { 0.0 } })
        tempMatrix.matrix[0][0] = (a11 * b11) + (a12 * b21)
        tempMatrix.matrix[0][1] = (a11 * b12) + (a12 * b22)
        tempMatrix.matrix[1][0] = (a21 * b11) + (a22 * b21)
        tempMatrix.matrix[1][1] = (a21 * b12) + (a22 * b22)

        return tempMatrix.matrix
    }
}