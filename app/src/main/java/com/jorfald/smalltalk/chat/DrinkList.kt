package com.jorfald.smalltalk.chat

import com.google.gson.annotations.SerializedName

data class DrinkList(
    @SerializedName(value = "drinks")
    var cocktails: List<Cocktail>
)

data class Cocktail(
    @SerializedName(value = "idDrink")
    val id: Int,
    @SerializedName(value = "strDrink")
    val drinkName: String,
    @SerializedName(value = "strInstructions")
    val instructions: String,
    @SerializedName(value = "strDrinkThumb")
    val drinkImageUrl: String,

    private val strIngredient1: String?,
    private val strIngredient2: String?,
    private val strIngredient3: String?,
    private val strIngredient4: String?,
    private val strIngredient5: String?,
    private val strIngredient6: String?,
    private val strIngredient7: String?,
    private val strIngredient8: String?,
    private val strIngredient9: String?,
    private val strIngredient10: String?,
    private val strIngredient11: String?,
    private val strIngredient12: String?,
    private val strIngredient13: String?,
    private val strIngredient14: String?,
    private val strIngredient15: String?,

    private val strMeasure1: String?,
    private val strMeasure2: String?,
    private val strMeasure3: String?,
    private val strMeasure4: String?,
    private val strMeasure5: String?,
    private val strMeasure6: String?,
    private val strMeasure7: String?,
    private val strMeasure8: String?,
    private val strMeasure9: String?,
    private val strMeasure10: String?,
    private val strMeasure11: String?,
    private val strMeasure12: String?,
    private val strMeasure13: String?,
    private val strMeasure14: String?,
    private val strMeasure15: String?
) {
    val ingredients: List<Ingredient>
        get() {
            val ingredientList = listOf(
                strIngredient1,
                strIngredient2,
                strIngredient3,
                strIngredient4,
                strIngredient5,
                strIngredient6,
                strIngredient7,
                strIngredient8,
                strIngredient9,
                strIngredient10,
                strIngredient11,
                strIngredient12,
                strIngredient13,
                strIngredient14,
                strIngredient15
            )

            val measureList = listOf(
                strMeasure1,
                strMeasure2,
                strMeasure3,
                strMeasure4,
                strMeasure5,
                strMeasure6,
                strMeasure7,
                strMeasure8,
                strMeasure9,
                strMeasure10,
                strMeasure11,
                strMeasure12,
                strMeasure13,
                strMeasure14,
                strMeasure15
            )

            val listWithoutNull = mutableListOf<Ingredient>()

            for ((index, ingredient) in ingredientList.withIndex()) {
                if (ingredient == null) {
                    break
                }

                listWithoutNull.add(
                    Ingredient(
                        ingredient,
                        measureList[index] ?: "-"
                    )
                )
            }

            return listWithoutNull
        }
}

data class Ingredient(
    val ingredient: String,
    val measure: String
)