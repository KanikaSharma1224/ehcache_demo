package com.demo.simple;

import java.util.ArrayList;
import java.util.List;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

public class EhcacheCreator {
	private static CacheManager cacheManager;
	private static Cache<String, ArrayList> cache;

	public EhcacheCreator() {
		cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build();
		cacheManager.init();

		cache = cacheManager.createCache("list of Employees", CacheConfigurationBuilder
				.newCacheConfigurationBuilder(String.class, ArrayList.class, ResourcePoolsBuilder.heap(10)));
	}

	static List<Employee> getEmployeeSource() {
		List<Employee> houseKeepingEmployees = new ArrayList<>();
		houseKeepingEmployees.add(new Employee("John", "Snow", "hok005", 34, "House Keeping"));
		houseKeepingEmployees.add(new Employee("Rick", "white", "hok003", 43, "House Keeping"));
		houseKeepingEmployees.add(new Employee("Robin", "rainfall", "hok009", 25, "House Keeping"));
		houseKeepingEmployees.add(new Employee("Jonas", "Nick", "hok011", 31, "House Keeping"));
		houseKeepingEmployees.add(new Employee("kerry", "Jona", "hok002", 39, "House Keeping"));

		return houseKeepingEmployees;

	}

	static List<Employee> getEmployees(String departmentName) {
		if (cache.containsKey(departmentName)) {
			return cache.get(departmentName);
		}

		cache.put(departmentName, (ArrayList<Employee>) getEmployeeSource());
		return cache.get(departmentName);
	}

	public static void main(String[] args) {
		EhcacheCreator cacheCreator = new EhcacheCreator();

		System.out.println("Employees : " + cacheCreator.getEmployees("House Keeping"));

	}

}
