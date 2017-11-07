(function() {
    'use strict';

    angular
        .module('appApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('votar-favorito-b', {
            parent: 'entity',
            url: '/votar-favorito-b',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appApp.votarFavoritoB.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/votar-favorito-b/votar-favorito-bs.html',
                    controller: 'VotarFavoritoBController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('votarFavoritoB');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('votar-favorito-b-detail', {
            parent: 'votar-favorito-b',
            url: '/votar-favorito-b/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appApp.votarFavoritoB.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/votar-favorito-b/votar-favorito-b-detail.html',
                    controller: 'VotarFavoritoBDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('votarFavoritoB');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'VotarFavoritoB', function($stateParams, VotarFavoritoB) {
                    return VotarFavoritoB.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'votar-favorito-b',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('votar-favorito-b-detail.edit', {
            parent: 'votar-favorito-b-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/votar-favorito-b/votar-favorito-b-dialog.html',
                    controller: 'VotarFavoritoBDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['VotarFavoritoB', function(VotarFavoritoB) {
                            return VotarFavoritoB.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('votar-favorito-b.new', {
            parent: 'votar-favorito-b',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/votar-favorito-b/votar-favorito-b-dialog.html',
                    controller: 'VotarFavoritoBDialogController',
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
                    $state.go('votar-favorito-b', null, { reload: 'votar-favorito-b' });
                }, function() {
                    $state.go('votar-favorito-b');
                });
            }]
        })
        .state('votar-favorito-b.edit', {
            parent: 'votar-favorito-b',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/votar-favorito-b/votar-favorito-b-dialog.html',
                    controller: 'VotarFavoritoBDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['VotarFavoritoB', function(VotarFavoritoB) {
                            return VotarFavoritoB.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('votar-favorito-b', null, { reload: 'votar-favorito-b' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('votar-favorito-b.delete', {
            parent: 'votar-favorito-b',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/votar-favorito-b/votar-favorito-b-delete-dialog.html',
                    controller: 'VotarFavoritoBDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['VotarFavoritoB', function(VotarFavoritoB) {
                            return VotarFavoritoB.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('votar-favorito-b', null, { reload: 'votar-favorito-b' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
