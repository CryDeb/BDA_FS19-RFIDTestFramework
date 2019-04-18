package test.cases.dataloader

class TestData(
    val id: Int,
    val name: String,
    val preParameters: List<String>,
    val postParameters: List<String>,
    val testType: TestType
) {
    constructor(id: Int, name: String, preParameters: List<String>, postParameters: List<String>, testType: String) :
            this(id, name, preParameters, postParameters, TestType.valueOf(testType))


    override fun equals(other: Any?): Boolean {
        if (super.equals(other))
            return true
        if (other is TestData) {
            if (arePropertiesEqual(other)) {
                return true
            }
        }
        return false
    }

    private fun arePropertiesEqual(other: TestData): Boolean {
        return id == other.id
                && name == other.name
                && preParameters == other.preParameters
                && postParameters == other.postParameters
                && testType == other.testType
    }

    override fun toString(): String {
        return "ID: %d, Name: %s, PreParams: %s, PostParams: %s, TestType: %s"
            .format(id, name, preParameters.toString(), postParameters.toString(), testType)
    }
}