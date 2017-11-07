(function() {
    'use strict';

    angular
        .module('appApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('votar-favorito-c', {
            parent: 'entity',
            url: '/votar-favorito-c',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appApp.votarFavoritoC.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/votar-favorito-c/votar-favorito-cs.html',
                    controller: 'VotarFavoritoCController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('votarFavoritoC');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('votar-favorito-c-detail', {
            parent: 'votar-favorito-c',
            url: '/votar-favorito-c/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appApp.votarFavoritoC.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/votar-favorito-c/votar-favorito-c-detail.html',
                    controller: 'VotarFavoritoCDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('votarFavoritoC');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'VotarFavoritoC', function($stateParams, VotarFavoritoC) {
                    return VotarFavoritoC.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'votar-favorito-c',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('votar-favorito-c-detail.edit', {
            parent: 'votar-favorito-c-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/votar-favorito-c/votar-favorito-c-dialog.html',
                    controller: 'VotarFavoritoCDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['VotarFavoritoC', function(VotarFavoritoC) {
                            return VotarFavoritoC.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('votar-favorito-c.new', {
            parent: 'votar-favorito-c',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/votar-favorito-c/votar-favorito-c-dialog.html',
                    controller: 'VotarFavoritoCDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                momento: null,
                                valoracio: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('votar-favorito-c', null, { reload: 'votar-favorito-c' });
                }, function() {
                    $state.go('votar-favorito-c');
                });
            }]
        })
        .state('votar-favorito-c.edit', {
            parent: 'votar-favorito-c',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/votar-favorito-c/votar-favorito-c-dialog.html',
                    controller: 'VotarFavoritoCDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['VotarFavoritoC', function(VotarFavoritoC) {
                            return VotarFavoritoC.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('votar-favorito-c', null, { reload: 'votar-favorito-c' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('votar-favorito-c.delete', {
            parent: 'votar-favorito-c',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/votar-favorito-c/votar-favorito-c-delete-dialog.html',
                    controller: 'VotarFavoritoCDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['VotarFavoritoC', function(VotarFavoritoC) {
                            return VotarFavoritoC.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('votar-favorito-c', null, { reload: 'votar-favorito-c' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
