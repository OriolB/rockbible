(function() {
    'use strict';

    angular
        .module('appApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('votar-favorito-m', {
            parent: 'entity',
            url: '/votar-favorito-m',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appApp.votarFavoritoM.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/votar-favorito-m/votar-favorito-ms.html',
                    controller: 'VotarFavoritoMController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('votarFavoritoM');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('votar-favorito-m-detail', {
            parent: 'votar-favorito-m',
            url: '/votar-favorito-m/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appApp.votarFavoritoM.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/votar-favorito-m/votar-favorito-m-detail.html',
                    controller: 'VotarFavoritoMDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('votarFavoritoM');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'VotarFavoritoM', function($stateParams, VotarFavoritoM) {
                    return VotarFavoritoM.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'votar-favorito-m',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('votar-favorito-m-detail.edit', {
            parent: 'votar-favorito-m-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/votar-favorito-m/votar-favorito-m-dialog.html',
                    controller: 'VotarFavoritoMDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['VotarFavoritoM', function(VotarFavoritoM) {
                            return VotarFavoritoM.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('votar-favorito-m.new', {
            parent: 'votar-favorito-m',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/votar-favorito-m/votar-favorito-m-dialog.html',
                    controller: 'VotarFavoritoMDialogController',
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
                    $state.go('votar-favorito-m', null, { reload: 'votar-favorito-m' });
                }, function() {
                    $state.go('votar-favorito-m');
                });
            }]
        })
        .state('votar-favorito-m.edit', {
            parent: 'votar-favorito-m',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/votar-favorito-m/votar-favorito-m-dialog.html',
                    controller: 'VotarFavoritoMDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['VotarFavoritoM', function(VotarFavoritoM) {
                            return VotarFavoritoM.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('votar-favorito-m', null, { reload: 'votar-favorito-m' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('votar-favorito-m.delete', {
            parent: 'votar-favorito-m',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/votar-favorito-m/votar-favorito-m-delete-dialog.html',
                    controller: 'VotarFavoritoMDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['VotarFavoritoM', function(VotarFavoritoM) {
                            return VotarFavoritoM.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('votar-favorito-m', null, { reload: 'votar-favorito-m' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
