package com.botrista.countertopbot.remote.hotpot


import com.google.gson.annotations.SerializedName

data class HotpotResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status")
    val status: Boolean
) {
    data class Data(
        @SerializedName("customize_add_on")
        val customizeAddOn: List<CustomizeAddOn>,
        @SerializedName("customize_alcohol_level")
        val customizeAlcoholLevel: List<CustomizeAlcoholLevel>,
        @SerializedName("customize_offset")
        val customizeOffset: List<CustomizeOffset>,
        @SerializedName("customize_sugar_level")
        val customizeSugarLevel: List<CustomizeSugarLevel>,
        @SerializedName("drink_settings")
        val drinkSettings: List<DrinkSetting>,
        @SerializedName("drinks")
        val drinks: List<Drink>,
        @SerializedName("flavor_categories")
        val flavorCategories: List<FlavorCategory>,
        @SerializedName("flavors")
        val flavors: List<Flavor>,
        @SerializedName("menu_id")
        val menuId: String,
        @SerializedName("menu_name")
        val menuName: String
    ) {
        data class CustomizeAddOn(
            @SerializedName("flavor_sku")
            val flavorSku: String,
            @SerializedName("max_level")
            val maxLevel: Int,
            @SerializedName("shot_ml")
            val shotMl: Double
        )

        data class CustomizeAlcoholLevel(
            @SerializedName("is_default")
            val isDefault: Boolean,
            @SerializedName("level")
            val level: Int,
            @SerializedName("name")
            val name: String
        )

        data class CustomizeOffset(
            @SerializedName("blending_duration")
            val blendingDuration: Int,
            @SerializedName("customize_level")
            val customizeLevel: Int,
            @SerializedName("drink_sku")
            val drinkSku: String,
            @SerializedName("flavors")
            val flavors: List<Flavor>
        ) {
            data class Flavor(
                @SerializedName("default_volume")
                val defaultVolume: Int,
                @SerializedName("ratio_offset")
                val ratioOffset: Double,
                @SerializedName("sku")
                val sku: String,
                @SerializedName("volume_ml_offset")
                val volumeMlOffset: Int
            )
        }

        data class CustomizeSugarLevel(
            @SerializedName("is_default")
            val isDefault: Boolean,
            @SerializedName("level")
            val level: Int,
            @SerializedName("name")
            val name: String
        )

        data class DrinkSetting(
            @SerializedName("allow_add_on")
            val allowAddOn: Boolean,
            @SerializedName("allow_sparkling")
            val allowSparkling: Boolean,
            @SerializedName("cup_sizes")
            val cupSizes: List<CupSize>,
            @SerializedName("customize_name")
            val customizeName: String,
            @SerializedName("drink_sku")
            val drinkSku: String,
            @SerializedName("is_default_sparkling")
            val isDefaultSparkling: Boolean
        ) {
            data class CupSize(
                @SerializedName("is_default")
                val isDefault: Boolean,
                @SerializedName("size_ml")
                val sizeMl: Int,
                @SerializedName("size_name")
                val sizeName: String
            )
        }

        data class Drink(
            @SerializedName("allow_sparkling")
            val allowSparkling: Boolean,
            @SerializedName("blender")
            val blender: Blender,
            @SerializedName("default_volume")
            val defaultVolume: Int,
            @SerializedName("drink_category")
            val drinkCategory: String,
            @SerializedName("fixed_level")
            val fixedLevel: Boolean,
            @SerializedName("flavors")
            val flavors: List<Flavor>,
            @SerializedName("name")
            val name: String,
            @SerializedName("photo")
            val photo: String,
            @SerializedName("process_order")
            val processOrder: List<String>,
            @SerializedName("sku")
            val sku: String
        ) {
            data class Blender(
                @SerializedName("duration")
                val duration: Int,
                @SerializedName("mode")
                val mode: String
            )

            data class Flavor(
                @SerializedName("name")
                val name: String,
                @SerializedName("ratio")
                val ratio: Double,
                @SerializedName("sku")
                val sku: String,
                @SerializedName("sugar_level")
                val sugarLevel: Int
            )
        }

        data class FlavorCategory(
            @SerializedName("target")
            val target: List<String>,
            @SerializedName("type")
            val type: String
        )

        data class Flavor(
            @SerializedName("add_on")
            val addOn: Boolean,
            @SerializedName("alcohol_by_volume")
            val alcoholByVolume: Int,
            @SerializedName("assigned_ingredient")
            val assignedIngredient: String,
            @SerializedName("calibration_ratio")
            val calibrationRatio: List<Int>,
            @SerializedName("cleaning_cadence")
            val cleaningCadence: Int,
            @SerializedName("full_sku")
            val fullSku: String,
            @SerializedName("name")
            val name: String,
            @SerializedName("nutrition_info")
            val nutritionInfo: NutritionInfo,
            @SerializedName("pulse_setting")
            val pulseSetting: Int,
            @SerializedName("shelf_life_days")
            val shelfLifeDays: Int,
            @SerializedName("specific_heat")
            val specificHeat: Int,
            @SerializedName("storage_method")
            val storageMethod: String,
            @SerializedName("temperature_model")
            val temperatureModel: String
        ) {
            data class NutritionInfo(
                @SerializedName("calcium_mg")
                val calciumMg: Int,
                @SerializedName("calories")
                val calories: Double,
                @SerializedName("cholesterol_mg")
                val cholesterolMg: Int,
                @SerializedName("dietary_fiber_g")
                val dietaryFiberG: Int,
                @SerializedName("includes_g_added_sugars")
                val includesGAddedSugars: Int,
                @SerializedName("iron_mg")
                val ironMg: Int,
                @SerializedName("niacin_mg")
                val niacinMg: Int,
                @SerializedName("potassium_mg")
                val potassiumMg: Int,
                @SerializedName("protein_g")
                val proteinG: Int,
                @SerializedName("saturated_fat_g")
                val saturatedFatG: Int,
                @SerializedName("sodium_mg")
                val sodiumMg: Double,
                @SerializedName("total_carbohydrate_g")
                val totalCarbohydrateG: Double,
                @SerializedName("total_fat_g")
                val totalFatG: Int,
                @SerializedName("total_sugars_g")
                val totalSugarsG: Double,
                @SerializedName("trans_fat_g")
                val transFatG: Int,
                @SerializedName("vitamin_c_mg")
                val vitaminCMg: Int,
                @SerializedName("vitamin_d_mcg")
                val vitaminDMcg: Int,
                @SerializedName("zinc_mg")
                val zincMg: Int
            )
        }
    }
}