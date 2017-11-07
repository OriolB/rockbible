(function() {
    'use strict';

    angular
        .module('appApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('votar-favorito-a', {
            parent: 'entity',
            url: '/votar-favorito-a',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appApp.votarFavoritoA.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/votar-favorito-a/votar-favorito-as.html',
                    controller: 'VotarFavoritoAController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('votarFavoritoA');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('votar-favorito-a-detail', {
            parent: 'votar-favorito-a',
            url: '/votar-favorito-a/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appApp.votarFavoritoA.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/votar-favorito-a/votar-favorito-a-detail.html',
                    controller: 'VotarFavoritoADetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('votarFavoritoA');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'VotarFavoritoA', function($stateParams, VotarFavoritoA) {
                    return VotarFavoritoA.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'votar-favorito-a',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('votar-favorito-a-detail.edit', {
            parent: 'votar-favorito-a-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/votar-favorito-a/votar-favorito-a-dialog.html',
                    controller: 'VotarFavoritoADialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['VotarFavoritoA', function(VotarFavoritoA) {
                            return VotarFavoritoA.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('votar-favorito-a.new', {
            parent: 'votar-favorito-a',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/votar-favorito-a/votar-favorito-a-dialog.html',
                    controller: 'VotarFavoritoADialogController',
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
                    $state.go('votar-favorito-a', null, { reload: 'votar-favorito-a' });
                }, function() {
                    $state.go('votar-favorito-a');
                });
            }]
        })
        .state('votar-favorito-a.edit', {
            parent: 'votar-favorito-a',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/votar-favorito-a/votar-favorito-a-dialog.html',
                    controller: 'VotarFavoritoADialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['VotarFavoritoA', function(VotarFavoritoA) {
                            return VotarFavoritoA.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('votar-favorito-a', null, { reload: 'votar-favorito-a' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('votar-favorito-a.delete', {
            parent: 'votar-favorito-a',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/votar-favorito-a/votar-favorito-a-delete-dialog.html',
                    controller: 'VotarFavoritoADeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['VotarFavoritoA', function(VotarFavoritoA) {
                            return VotarFavoritoA.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('votar-favorito-a', null, { reload: 'votar-favorito-a' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
