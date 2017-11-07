(function() {
    'use strict';

    angular
        .module('appApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('genero', {
            parent: 'entity',
            url: '/genero',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appApp.genero.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/genero/generos.html',
                    controller: 'GeneroController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('genero');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('genero-detail', {
            parent: 'genero',
            url: '/genero/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appApp.genero.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/genero/genero-detail.html',
                    controller: 'GeneroDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('genero');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Genero', function($stateParams, Genero) {
                    return Genero.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'genero',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('genero-detail.edit', {
            parent: 'genero-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/genero/genero-dialog.html',
                    controller: 'GeneroDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Genero', function(Genero) {
                            return Genero.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('genero.new', {
            parent: 'genero',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/genero/genero-dialog.html',
                    controller: 'GeneroDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('genero', null, { reload: 'genero' });
                }, function() {
                    $state.go('genero');
                });
            }]
        })
        .state('genero.edit', {
            parent: 'genero',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/genero/genero-dialog.html',
                    controller: 'GeneroDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Genero', function(Genero) {
                            return Genero.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('genero', null, { reload: 'genero' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('genero.delete', {
            parent: 'genero',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/genero/genero-delete-dialog.html',
                    controller: 'GeneroDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Genero', function(Genero) {
                            return Genero.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('genero', null, { reload: 'genero' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
