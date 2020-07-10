package com.ovchinnikovm.android.vktop.groups;

public class GroupsInteractorImpl implements GroupsInteractor {
    private GroupsRepository repository;

    public GroupsInteractorImpl(GroupsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void getGroups() {
        repository.getGroups();
    }

    @Override
    public void getGlobalGroups(String query) {
        repository.getGlobalGroups(query);
    }
}
