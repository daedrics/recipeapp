package com.recipeapp.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Repository;

import com.recipeapp.entity.UserEntity;
import com.recipeapp.exception.NotFound;
import com.recipeapp.exception.RecipeAppNotFoundException;

@Repository
public class UserRepositoryImpl implements UserRepository {

	@PersistenceContext
	private EntityManager entityManager;

	public UserEntity save(UserEntity user) {
		entityManager.persist(user);
		return user;
	}

	public UserEntity find(int id) {
		String queryString = "FROM UserEntity user WHERE user.id =?1";
		try {
			return entityManager.createQuery(queryString, UserEntity.class).setParameter(1, id).getSingleResult();
		} catch (Exception e) {
			throw new RecipeAppNotFoundException(NotFound.USER);
		}
	}

	@Override
	public UserEntity update(UserEntity user) {
		entityManager.merge(user);
		return user;
	}

	@Override
	public UserEntity findByUsername(String username) {
		String queryString = "From UserEntity user where user.username =?1";
		try {
			return entityManager.createQuery(queryString, UserEntity.class).setParameter(1, username).getSingleResult();
		} catch (Exception e) {
			throw new BadCredentialsException("wrong username or password");
		}
	}

	@Override
	public Boolean ifExist(String username) {
		String queryString = "SELECT COUNT (user) FROM UserEntity user where user.username =?1";
		Query query = entityManager.createQuery(queryString).setParameter(1, username);
		Long count = (Long) query.getSingleResult();
		return count.equals(1L);
	}
}
