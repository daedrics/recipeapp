package com.recipeapp.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.recipeapp.entity.RecipeEntity;
import com.recipeapp.exception.NotFound;
import com.recipeapp.exception.RecipeAppNotFoundException;

@Repository
public class RecipeRepositoryImpl implements RecipeRepository {

	private static final int MAX_RECIPES = 10;

	@PersistenceContext
	private EntityManager entityManager;

	public RecipeEntity save(RecipeEntity recipe) {
		entityManager.persist(recipe);
		return recipe;
	}

	public RecipeEntity find(int id) {
		String query = "SELECT recipe FROM RecipeEntity recipe left join fetch recipe.user user WHERE recipe.id =?1";
		try {
			return entityManager.createQuery(query, RecipeEntity.class).setParameter(1, id).getSingleResult();
		} catch (Exception e) {
			throw new RecipeAppNotFoundException(NotFound.RECIPE);
		}
	}

	@Override
	public RecipeEntity update(RecipeEntity recipe) {
		entityManager.merge(recipe);
		return recipe;
	}

	@Override
	public void delete(RecipeEntity recipe) {
		entityManager.remove(recipe);
	}

	@Override
	public List<RecipeEntity> latestPublished() {
		String queryString = "From RecipeEntity recipe where recipe.published = true order by recipe.publishedOn desc";
		TypedQuery<RecipeEntity> query = entityManager.createQuery(queryString, RecipeEntity.class);
		query.setMaxResults(MAX_RECIPES);
		return query.getResultList();
	}

	@Override
	public List<RecipeEntity> searchRecipesByName(String name) {
		String queryString = "FROM RecipeEntity recipe where recipe.name LIKE?1 AND recipe.published=true";
		TypedQuery<RecipeEntity> query = entityManager.createQuery(queryString, RecipeEntity.class);
		query.setParameter(1, "%" + name + "%");
		return query.getResultList();
	}

	public int userTotalRecipes(int userId, String name) {
		StringBuilder queryString = new StringBuilder(" From RecipeEntity recipe where recipe.user.id =?1");

		if (name != null && !name.isEmpty()) {
			queryString.append(" AND recipe.name LIKE?2");

		}
		TypedQuery<RecipeEntity> query = entityManager.createQuery(queryString.toString(), RecipeEntity.class);
		query.setParameter(1, userId);

		if (name != null && !name.isEmpty()) {
			query.setParameter(2, "%" + name + "%");
		}

		return query.getResultList().size();
	}

	public List<RecipeEntity> userRecipes(Integer userId, Integer pageNumber, Integer pageSize, String name) {

		pageNumber = (pageNumber - 1) * pageSize;
		StringBuilder queryString = new StringBuilder(
				"From RecipeEntity recipe left join fetch recipe.user user where user.id =?1");

		if (name != null && !name.isEmpty()) {
			queryString.append(" AND recipe.name LIKE?2");

		}

		TypedQuery<RecipeEntity> query = entityManager.createQuery(queryString.toString(), RecipeEntity.class)
				.setFirstResult(pageNumber).setMaxResults(pageSize).setParameter(1, userId);
		if (name != null && !name.isEmpty()) {
			query.setParameter(2, "%" + name + "%");
		}

		return query.getResultList();
	}
}
