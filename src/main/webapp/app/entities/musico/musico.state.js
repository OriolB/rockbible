(function() {
    'use strict';

    angular
        .module('appApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('musico', {
            parent: 'entity',
            url: '/musico',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appApp.musico.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/musico/musicos.html',
                    controller: 'MusicoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('musico');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('musico-detail', {
            parent: 'musico',
            url: '/musico/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appApp.musico.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/musico/musico-detail.html',
                    controller: 'MusicoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('musico');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Musico', function($stateParams, Musico) {
                    return Musico.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'musico',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('musico-detail.edit', {
            parent: 'musico-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/musico/musico-dialog.html',
                    controller: 'MusicoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Musico', function(Musico) {
                            return Musico.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('musico.new', {
            parent: 'musico',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/musico/musico-dialog.html',
                    controller: 'MusicoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                fechaNacimiento: null,
                                descripcion: null,
                                foto: null,
                                fotoContentType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('musico', null, { reload: 'musico' });
                }, function() {
                    $state.go('musico');
                });
            }]
        })
        .state('musico.edit', {
            parent: 'musico',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/musico/musico-dialog.html',
                    controller: 'MusicoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Musico', function(Musico) {
                            return Musico.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('musico', null, { reload: 'musico' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('musico.delete', {
            parent: 'musico',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/musico/musico-delete-dialog.html',
                    controller: 'MusicoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Musico', function(Musico) {
                            return Musico.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('musico', null, { reload: 'musico' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
