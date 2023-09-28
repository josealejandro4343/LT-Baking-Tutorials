package com.example.ltbakingtutorials;

public class Recipes {
    String RecipeName, RecipeImage;
    Integer  Difficulty;

    Recipes() {

    }

    public Recipes(String recipeName, String recipeImage, Integer Difficulty) {
        RecipeName = recipeName;
        RecipeImage = recipeImage;
        this.Difficulty = Difficulty;
    }

    public String getRecipeName() {
        return RecipeName;
    }

    public void setRecipeName(String recipeName) {
        RecipeName = recipeName;
    }

    public String getRecipeImage() {
        return RecipeImage;
    }

    public void setRecipeImage(String recipeImage) {
        RecipeImage = recipeImage;
    }

    public Integer getDifficulty() {
        return Difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.Difficulty = difficulty;
    }
}
