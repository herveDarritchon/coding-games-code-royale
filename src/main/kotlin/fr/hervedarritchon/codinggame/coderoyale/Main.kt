import java.util.*

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
fun main(args: Array<String>) {
    val input = Scanner(System.`in`)
    val numSites = input.nextInt()
    val mapOfBuildingSitesAtStartUp: MutableMap<Int, Spot> = mutableMapOf()

    for (i in 0 until numSites) {
        val siteId = input.nextInt()
        val x = input.nextInt()
        val y = input.nextInt()
        val radius = input.nextInt()
        mapOfBuildingSitesAtStartUp[siteId] = Spot(Coordinates(x, y), radius)
    }

    // game loop
    while (true) {
        val gold = input.nextInt()
        val touchedSite = input.nextInt() // -1 if none
        val mapOfMyBuildingSites: MutableMap<Int, MyBuildingSite> = mutableMapOf()
        val mapOfOpponentBuildingSites: MutableMap<Int, OpponentBuildingSite> = mutableMapOf()
        val mapOfNeutralBuildingSites: MutableMap<Int, NeutralBuildingSite> = mutableMapOf()

        for (i in 0 until numSites) {
            val siteId = input.nextInt()
            val ignore1 = input.nextInt() // used in future leagues
            val ignore2 = input.nextInt() // used in future leagues
            val structureType = input.nextInt() // -1 = No structure, 2 = Barracks
            val owner = input.nextInt() // -1 = No structure, 0 = Friendly, 1 = Enemy
            val param1 = input.nextInt()
            val param2 = input.nextInt()
            when {
                OwnerEnum.fromInt(owner) == OwnerEnum.Friendly -> mapOfMyBuildingSites[siteId] = MyBuildingSite(
                    siteId,
                    mapOfBuildingSitesAtStartUp[siteId],
                    ignore1,
                    ignore2,
                    BuildingTypeEnum.fromInt(structureType),
                    param1,
                    param2
                )
                OwnerEnum.fromInt(owner) == OwnerEnum.Enemy -> mapOfOpponentBuildingSites[siteId] =
                    OpponentBuildingSite(
                        siteId,
                        mapOfBuildingSitesAtStartUp[siteId],
                        ignore1,
                        ignore2,
                        BuildingTypeEnum.fromInt(structureType),
                        param1,
                        param2
                    )
                else -> {
                    mapOfNeutralBuildingSites[siteId] = NeutralBuildingSite(
                        siteId,
                        mapOfBuildingSitesAtStartUp[siteId],
                        ignore1,
                        ignore2,
                        BuildingTypeEnum.fromInt(structureType),
                        param1,
                        param2
                    )
                }
            }

        }

        val listOfUnits: MutableList<Unit> = mutableListOf()
        val numUnits = input.nextInt()
        for (i in 0 until numUnits) {
            val x = input.nextInt()
            val y = input.nextInt()
            val owner = input.nextInt()
            val unitType = input.nextInt() // -1 = QUEEN, 0 = KNIGHT, 1 = ARCHER
            val health = input.nextInt()
            listOfUnits.add(
                Unit(
                    Coordinates(x, y),
                    OwnerEnum.fromInt(owner),
                    UnitTypeEnum.fromInt(unitType),
                    health
                )
            )
        }

        val queen = Queen(wealth = gold, touchedSite = mapOfMyBuildingSites[touchedSite])

        // Write an action using println()
        // To debug: System.err.println("Debug messages...")
        System.err.println("Debug messages... queen wealth is ${queen.wealth}.")
        System.err.println("Debug messages... Owner Building sites ${mapOfMyBuildingSites.size}.")
        System.err.println("Debug messages... Opponent Building sites ${mapOfOpponentBuildingSites.size}.")
        System.err.println("Debug messages... Neutral Building sites ${mapOfNeutralBuildingSites.size}.")


        // First line: A valid queen action
        // Second line: A set of training instructions
        println("WAIT")
        println("TRAIN")
    }
}


data class Cell(val value: Int)

class Map {
    private val width: Int = 1920
    private val height: Int = 1000
    private var field: Array<Cell> = Array(height * width) { Cell(0) }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Map) return false

        if (!field.contentDeepEquals(other.field)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = field.contentDeepHashCode()
        result = 31 * result + height
        result = 31 * result + width
        return result
    }

    fun get(row: Int, column: Int): Int {
        return 0
    }

}

data class Spot(val coordinates: Coordinates, val radius: Int)

data class Coordinates(val x: Int, val y: Int)

data class Unit(
    val coordinates: Coordinates,
    val ownerEnum: OwnerEnum,
    val unitTypeEnum: UnitTypeEnum,
    val health: Int
)

data class Queen(
    var hitPoints: Int = 100,
    var wealth: Int = 100,
    val touchedSite: MyBuildingSite?
) {
    private val radius: Int = 30
    private val pace: Int = 60 // Movement by turn 60 units
}

data class Building(val type: BuildingEnum)

interface BuildingSite

data class MyBuildingSite(
    val id: Int,
    val spot: Spot?,
    val ignore1: Int,
    val ignore2: Int,
    val structureType: BuildingTypeEnum,
    val param1: Int,
    val param2: Int
) : BuildingSite

data class OpponentBuildingSite(
    val id: Int,
    val spot: Spot?,
    val ignore1: Int,
    val ignore2: Int,
    val structureType: BuildingTypeEnum,
    val param1: Int,
    val param2: Int
) : BuildingSite

data class NeutralBuildingSite(
    val id: Int,
    val spot: Spot?,
    val ignore1: Int,
    val ignore2: Int,
    val structureType: BuildingTypeEnum,
    val param1: Int,
    val param2: Int
) : BuildingSite

enum class OwnerEnum(val key: Int) {
    NoStructure(-1),
    Friendly(0),
    Enemy(2);

    companion object {
        private val map = OwnerEnum.values().associateBy(OwnerEnum::key)
        fun fromInt(key: Int) = map[key] ?: OwnerEnum.NoStructure
    }

}

enum class BuildingTypeEnum(val key: Int) {
    NoStructure(-1),
    Barracks(2);

    companion object {
        private val map = BuildingTypeEnum.values().associateBy(BuildingTypeEnum::key)
        fun fromInt(key: Int) = map[key] ?: BuildingTypeEnum.NoStructure
    }
}

enum class UnitTypeEnum(val key: Int) {
    Queen(-1),
    Knight(0),
    Archer(1);

    companion object {
        private val map = UnitTypeEnum.values().associateBy(UnitTypeEnum::key)
        fun fromInt(key: Int) = map[key] ?: UnitTypeEnum.Queen
    }
}

enum class BuildingEnum(val key: Int) {
    NoStructure(-1),
    Knight(1),
    Archer(2);

    companion object {
        private val map = BuildingEnum.values().associateBy(BuildingEnum::key)
        fun fromInt(key: Int) = map[key] ?: NoStructure
    }
}
