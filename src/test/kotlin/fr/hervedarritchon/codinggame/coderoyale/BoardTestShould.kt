package fr.hervedarritchon.codinggame.coderoyale

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * Created by Hervé Darritchon on 2019-03-29.
 */
internal class BoardTestShould {

    @Test
    internal fun `return a free cell from an empty board for any cell`() {
        val map = Map()

        Assertions.assertEquals(0, map.get(0, 0))

    }
}