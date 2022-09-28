package com.G26.fitnessandnutritionbuddy.data.profile;


public class ProfileRepository {
    private static volatile ProfileRepository instance;

    private ProfileDataSource dataSource;

    private ProfileRepository(ProfileDataSource dataSource) {
        this.dataSource = dataSource;
    }


}
