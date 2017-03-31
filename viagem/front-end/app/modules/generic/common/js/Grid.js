"use strict";

// Fix para bug da função SWAP do NG Grid
$.swap = function (elem, options, callback, args) {
    var ret, name, old = {};

    // Remember the old values, and insert the new ones
    for (name in options) {
        old[name] = elem.style[name];
        elem.style[name] = options[name];
    }

    ret = callback.apply(elem, args || []);

    // Revert the old values
    for (name in options) {
        elem.style[name] = old[name];
    }

    return ret;
};

angular.module("Generic")
  .directive('grid', ['$timeout', function ($timeout) {
      var gridColumns, searchTemplate, searchFilters;

      return {
          restrict: 'E',
          scope: {
              templates: '=?',
              manager: '=?',
              multiSelect: '=?',
              selections: '=?',
              columns: '=?',
              sizeDetail: '@',
              myData: '=?collections',
              autoStore: '=?',
              list: '@',
              rowHeight: '=?',
              searchField: '@',
              hasTools: '=?',
              hasEdit: '&?',
              hasDelete: '&?',
              enablePaging: '=?',
              showFooter: '=?',
              showSelectionCheckbox: '=?',
              readOnly: '&?',
              sortable: '=?',
              remote: '=?'
          },
          template: function (a, b) {
              var columns = a.find("grid-column"), search = a.find("grid-search");

              //Colunas
              gridColumns = {};

              if (columns.length > 0) {
                  columns.each(function (k, v) {
                      var el = $(v), field = el.attr('field'), column = [];

                      gridColumns[field] = {
                          name: field,
                          template: el.html()
                      };
                  });
              }
              //

              //Campos de Filtro
              searchFilters = {};

              if (search.html()) {
                  search.find('*[search-field]').each(function (k, v) {
                      var v = $(v),
                        fieldName = v.attr('search-field'),
                        condition = '';

                      if (v.attr('search-condition') !== '' && angular.isDefined(v.attr('search-condition'))){
                          condition = v.attr('search-condition');
                      } else {
                          condition = '=';
                      }

                      v.attr('ng-model', 'filter[\'' + fieldName + '_' + k + '\']');

                      searchFilters[fieldName + '_' + k] = {
                          field: fieldName,
                          condition: condition
                      };
                  });
              }

              searchTemplate = search.html() ? search.html() : '';
              //

              return "<div class=\"gridStyle\" ng-grid=\"gridOptions\"></div>"
          },
          link: function (scope, el, attrs, ctrl, transclude) {
              var grid = jQuery(el[0].children[0]),
                interval = 0;

              interval = setInterval(function () {
                  if (grid.is(':visible')) {
                      scope.gridOptions.$gridServices.DomUtilityService.RebuildGrid(
                        scope.gridOptions.$gridScope,
                        scope.gridOptions.ngGrid
                      );

                      clearTimeout(interval);
                  }
              }, 100);

              $timeout(function () {
                  scope.$$childHead.footerStyle = function () {
                      return {width: "100%", height: "auto"};
                  };
              }, 100);

              scope.$$childHead.topPanelHeight = function () {
                  return 'auto';
              };
          },
          controller: ['$scope', '$element', '$state', '$uibModal', '$filter', 'utils', 'notificationService', 'EVENTS', 'dialogs', '$q', '$templateCache',
              function ($scope, $element, $state, $uibModal, $filter, utils, nS, EVENTS, dialogs, $q, $templateCache) {
                  // headerCellTemplate = '<div class="ngHeaderSortColumn {{col.headerClass}}" ng-style="{\'cursor\': col.cursor}" ng-class="{ \'ngSorted\': !noSortVisible }"><div ng-click="col.sort($event); sort();" ng-class="\'colt\' + col.index" class="ngHeaderText">{{col.displayName}}</div><div class="ngSortButtonDown" ng-show="col.showSortButtonDown()"></div><div class="ngSortButtonUp" ng-show="col.showSortButtonUp()"></div><div class="ngSortPriority">{{col.sortPriority}}</div><div ng-class="{ ngPinnedIcon: col.pinned, ngUnPinnedIcon: !col.pinned }" ng-click="togglePin(col)" ng-show="col.pinnable"></div></div><div ng-show="col.resizable" class="ngHeaderGrip" ng-click="col.gripClick($event)" ng-mousedown="col.gripOnMouseDown($event)"></div>',
                  // headerCellTemplate = $templateCache.get("headerCellTemplate.html")
                  var headerCellTemplate = '<div class="ngHeaderSortColumn {{col.headerClass}}" ng-style="{\'cursor\': col.cursor}" ng-class="{ \'ngSorted\': !noSortVisible }"><div ng-click="col.sort($event); sort();" ng-class="\'colt\' + col.index" class="ngHeaderText">{{col.displayName}}</div><div class="ngSortButtonDown" ng-show="col.showSortButtonDown()"></div><div class="ngSortButtonUp" ng-show="col.showSortButtonUp()"></div><div class="ngSortPriority">{{col.sortPriority}}</div><div ng-class="{ ngPinnedIcon: col.pinned, ngUnPinnedIcon: !col.pinned }" ng-click="togglePin(col)" ng-show="col.pinnable"></div></div><div ng-show="col.resizable" class="ngHeaderGrip" ng-click="col.gripClick($event)" ng-mousedown="col.gripOnMouseDown($event)"></div>',
                    save = null,
                    cancel = null,
                    columns = [],
                    filters = [],
                    searchFields = searchFilters,
                    sort = '';

                  if (angular.isUndefined($scope.hasTools))
                      $scope.hasTools = true;

                  if(angular.isUndefined($scope.hasEdit))
                      $scope.hasEdit = $scope.$parent.hasEdit;
                  if(typeof $scope.hasEdit == 'boolean'){
                      var varHasEdit = $scope.hasEdit;
                      $scope.hasEdit = function(){return varHasEdit;};
                  }else if(!angular.isFunction($scope.hasEdit))
                      $scope.hasEdit = function(){return true;};

                  if(angular.isUndefined($scope.hasDelete))
                      $scope.hasDelete = $scope.$parent.hasDelete;
                  if(typeof $scope.hasDelete == 'boolean'){
                      var varHasDelete = $scope.hasDelete;
                      $scope.hasDelete = function(){return varHasDelete;};
                  }else if(!angular.isFunction($scope.hasDelete))
                      $scope.hasDelete = function(){return true;};

                  if (angular.isUndefined($scope.readOnly))
                      $scope.readOnly = $scope.$parent.readOnly;
                  if(typeof $scope.readOnly == 'boolean'){
                      var varReadOnly = $scope.readOnly;
                      $scope.readOnly = function(){return varReadOnly;};
                  }else if(!angular.isFunction($scope.readOnly))
                      $scope.readOnly = function(){return false;};

                  if (angular.isUndefined($scope.remote))
                      $scope.remote = true;

                  if (angular.isUndefined($scope.templates))
                      $scope.templates = $scope.$parent.templates;

                  if (angular.isFunction($scope.manager))
                      $scope.manager = $scope.manager();
                  else if (angular.isUndefined($scope.manager))
                      $scope.manager = $scope.$parent.manager;

                  if (angular.isUndefined($scope.multiSelect))
                      $scope.multiSelect = $scope.$parent.multiSelect;
                  if (angular.isUndefined($scope.showSelectionCheckbox))
                      $scope.showSelectionCheckbox = $scope.$parent.showSelectionCheckbox;
                  if (angular.isUndefined($scope.showSelectionCheckbox))
                      $scope.showSelectionCheckbox = true;
                  if (angular.isUndefined($scope.selections))
                      $scope.selections = $scope.$parent.selections;
                  if (angular.isUndefined($scope.selections))
                      $scope.selections = $scope.$parent.selections = [];
                  if (angular.isUndefined($scope.columns))
                      $scope.columns = $scope.$parent.columns;
                  if (angular.isUndefined($scope.sizeDetail))
                      $scope.sizeDetail = '';
                  if (angular.isUndefined($scope.myData))
                      $scope.myData = {};
                  if (angular.isUndefined($scope.autoStore))
                      $scope.autoStore = true;
                  if (angular.isUndefined($scope.list))
                      $scope.list = $scope.$parent.list;
                  if (angular.isDefined($scope.list))
                      $scope.autoStore = false;
                  if (angular.isDefined($scope.$parent.detailScope))
                      $scope.detailScope = $scope.$parent.detailScope;
                  if (angular.isDefined($scope.$parent.rowTemplate))
                      $scope.rowTemplate = $scope.$parent.rowTemplate;

                  $scope.sortInfo = {
                      fields: [],
                      directions: [],
                      columns: []
                  };

                  $scope.totalServerItems = 0;

                  $scope.setPagingData = function (data, page, pageSize) {
                      var pagedData = data;
                      var maxRows = Math.max($scope.totalServerItems, $scope.manager.getTotal());
                      var maxPages = Math.ceil(maxRows / pageSize);

                      if(!$scope.remote){
                          pagedData = data.slice((page - 1) * pageSize, page * pageSize);
                      }

                      $scope.myData = pagedData;
                      $scope.totalServerItems = $scope.manager.getTotal();

                      if (maxPages < page) {
                          maxPages = maxPages == 0 ? 1 : maxPages;
                          $scope.gridOptions.pagingOptions.currentPage = maxPages;
                      }

                  };

                  $scope.getPagedDataAsync = function (pageSize, page, filter, remote) {
                      var start = (page - 1) * pageSize,
                        limit = pageSize;

                      filter = filter || filters;
                      remote = angular.isDefined(remote) ? remote : true;

                      if(remote){
                          $scope.$emit(EVENTS.wait);

                          $scope.manager.find({
                              page: page,
                              start: start,
                              limit: Number(limit),
                              data: filter,
                              sort: ($scope.sortInfo.columns.length > 0 ? String($scope.sortInfo.columns[0].field).replace(/\$data./g, '') : ''),
                              dir: ($scope.sortInfo.directions.length > 0 ? $scope.sortInfo.directions[0] : '')
                          }).then(function (largeLoad) {
                              try{
                                  $scope.$emit(EVENTS.gridload, $scope);
                                  $scope.setPagingData($scope.manager.getCollection(), page, pageSize);
                              }catch(e){
                                  nS.error(e.message);
                              }
                              $scope.$emit(EVENTS.ready);
                          }, function (e) {
                              $scope.$emit(EVENTS.ready);
                              nS.error(e.message);
                          });

                      }else{
                          $scope.setPagingData($scope.manager.getCollection(), page, pageSize);
                      }

                  };

                  $scope.$watch('gridOptions.pagingOptions', function (newVal, oldVal) {
                      if (newVal !== oldVal && (newVal.currentPage !== oldVal.currentPage || newVal.pagSize !== oldVal.pageSize)) {
                          $scope.getPagedDataAsync($scope.gridOptions.pagingOptions.pageSize, $scope.gridOptions.pagingOptions.currentPage, null, $scope.remote);
                      }
                  }, true);

                  $scope.searchGrid = function () {
                      filters = [];

                      $.each($scope.filter, function (k, v) {
                          if (angular.isDefined(searchFields[k]) && angular.isDefined(v) && v !== null) {
                              filters.push({
                                  field: searchFields[k].field,
                                  condition: searchFields[k].condition,
                                  value: v
                              });
                          }
                      });

                      $scope.getPagedDataAsync($scope.gridOptions.pagingOptions.pageSize, $scope.gridOptions.pagingOptions.currentPage, filters, $scope.remote);
                  };

                  $scope.sort = function () {
                      var newSort = ''
                      if($scope.sortInfo.fields.length > 0){
                          newSort = $scope.sortInfo.fields[0]+$scope.sortInfo.directions[0];
                          if(newSort != sort){
                              sort = newSort;
                              $scope.getPagedDataAsync($scope.gridOptions.pagingOptions.pageSize, $scope.gridOptions.pagingOptions.currentPage, null, $scope.remote);
                          }
                      }
                  };

                  angular.forEach($scope.columns, function (v, k) {
                      var fieldName = v.field,
                        ns = String(v.field).split(".");

                      v.headerCellTemplate = headerCellTemplate;

                      for (var kn in ns) {
                          if (kn === "0")
                              v.field = "$data." + ns[kn];
                          else {
                              if (String(ns[kn]).charAt(0) === "$"){
                                  v.field += ".$data." + String(ns[kn]).substr(1);
                              } else {
                                  v.field += "." + ns[kn];
                              }
                          }
                      }

                      if (angular.isDefined(v.renderer)) {
                          v.cellTemplate = '<div class="ngCellText" ng-class="col.colIndex()" style="vertical-align: middle; display: table-cell;"><span ng-cell-text>{{ col.colDef.renderer(COL_FIELD, row.entity) }}</span></div>';
                      } else if (angular.isDefined(gridColumns[fieldName])) {
                          v.cellTemplate = '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>' + gridColumns[fieldName].template + '</span></div>';
                      } else {
                          v.cellTemplate = '<div class="ngCellText" ng-class="col.colIndex()" style="vertical-align: middle; display: table-cell;"><span ng-cell-text>{{ COL_FIELD }}</span></div>';
                      }

                      columns[k] = v;
                  });

                  //if ($scope.readOnly){
                  //    columns[columns.length] = {displayName: "", width: 100, cellTemplate: "<div class=\"ngCellText\" ng-class=\"col.colIndex()\" align=\"center\" style=\"vertical-align: middle; display: table-cell;\"><a href=\"\" class=\"btn btn-white btn-w-s btn-sm\" ng-click=\"unselect(); openDetail(row.entity, true);\"> <i class=\"fa fa-external-link\"></i> Visualizar </a></div>"};
                  //} else {
                  columns[columns.length] = {displayName: "", width: 100, cellTemplate: "<div class=\"ngCellText\" ng-class=\"col.colIndex()\" align=\"center\" style=\"vertical-align: middle; display: table-cell;\"><a href=\"\" class=\"btn btn-white btn-w-s btn-sm\" ng-click=\"unselect(); openDetail(row.entity);\" ng-if=\"hasEdit(row.entity, row) && !readOnly(row.entity, row)\"> <i class=\"fa fa-pencil\"></i> Editar </a></div>"};
                  columns[columns.length] = {
                      displayName: "",
                      width: 100,
                      cellTemplate: "<div class=\"ngCellText\" ng-class=\"col.colIndex()\" align=\"center\" style=\"vertical-align: middle; display: table-cell;\"><a href=\"\" class=\"btn btn-white btn-w-s btn-sm\" ng-click=\"unselect(); remove([row.entity]);\" ng-if=\"hasDelete(row.entity, row) && !readOnly(row.entity, row)\"> <i class=\"fa fa-trash\"></i> Remover </a> <a href=\"\" class=\"btn btn-white btn-w-s btn-sm\" ng-click=\"unselect(); openDetail(row.entity, true);\" ng-if=\"readOnly(row.entity, row)\"> <i class=\"fa fa-eye\"></i> Visualizar </a></div>"};
                  //}

                  //Definições de Filtros
                  $scope.filter = {};

                  if (angular.isDefined($scope.searchField)) {
                      searchFields[$scope.searchField] = {
                          field: $scope.searchField,
                          condition: '=%'
                      };

                      searchTemplate = $templateCache.get("searchTemplate.html");
                  } else {

                  }
                  if (searchTemplate !== '') {
                      $scope.datetime = (new Date).valueOf();
                      $templateCache.put('gridSearch' + $scope.datetime + '.html', searchTemplate);
                  }

                  $scope.gridOptions = {
                      data: 'myData',
                      headerRowHeight: 35,
                      footerRowHeight: 50,
                      i18n: 'pt-br',
                      enablePaging: angular.isDefined($scope.enablePaging) ? $scope.enablePaging : true,
                      showFooter: angular.isDefined($scope.showFooter) ? $scope.showFooter : true,
                      totalServerItems: 'totalServerItems',
                      multiSelect: (angular.isDefined($scope.multiSelect) ? $scope.multiSelect : false),
                      showFilter: true,
                      columnDefs: columns,
                      selectedItems: $scope.selections,
                      sortInfo: $scope.sortInfo,
                      showSelectionCheckbox: $scope.showSelectionCheckbox == true && $scope.multiSelect === true ? true : false,
                      useExternalSorting: angular.isDefined($scope.sortable) ? $scope.sortable : true,
                      rowHeight: $scope.rowHeight ? $scope.rowHeight : 40,
                      pagingOptions: {
                          pageSizes: [20, 40, 50],
                          pageSize: 20,
                          currentPage: 1
                      },
                      filterOptions: {
                          filterText: "",
                          useExternalFilter: true
                      },
                      beforeSelectionChange: function (rowItem, event) {
                          if (!$scope.multiSelect)
                              $scope.unselect();
                          return true;
                      }
                  };
                  if($scope.rowTemplate){
                      $scope.gridOptions.rowTemplate = $scope.rowTemplate;
                  }

                  $scope.unselect = function () {
                      angular.forEach($scope.myData, function (data, index) {
                          $scope.gridOptions.selectItem(index, false);
                      });
                  };

                  $scope.remove = function (objs) {
                      var objs = objs || $scope.selections,
                        dlg = null;

                      if (objs.length > 0) {
                          dlg = dialogs.confirm('Remover', 'Deseja realmente remover esta(s) opção(s)?');
                          dlg.result.then(function (btn) {
                              $scope.$emit(EVENTS.wait);

                              if ($scope.autoStore) {
                                  $scope.manager.remove(objs).then(function (rs) {
                                      $scope.$emit(EVENTS.ready);
                                      nS.success("Registro(s) removido(s) com sucesso!");
                                      $scope.getPagedDataAsync($scope.gridOptions.pagingOptions.pageSize, $scope.gridOptions.pagingOptions.currentPage);
                                  }, function (e) {
                                      $scope.$emit(EVENTS.ready);
                                      nS.error(e.message);
                                  });
                              } else {
                                  angular.forEach(objs, function (v, k) {
                                      angular.forEach($scope.myData, function (v2, k2) {
                                          if (v2 === v) {
                                              $scope.myData.splice(k2, 1);
                                          }
                                      });
                                  });

                                  $scope.$emit(EVENTS.ready);
                              }
                          }, function (btn) {

                          });
                      }
                  };

                  function rebuild(){
                      $scope.gridOptions.$gridServices.DomUtilityService.RebuildGrid(
                        $scope.gridOptions.$gridScope,
                        $scope.gridOptions.ngGrid
                      );
                  }
                  $scope.$on(EVENTS.gridresize, function(){
                      rebuild();
                  });
                  $timeout(function(){
                      rebuild();
                  }, 400);

                  if (angular.isUndefined($scope.addEvent)) {
                      if (angular.isDefined($scope.$parent.openDetail)) {
                          $scope.openDetail = $scope.$parent.openDetail;
                      } else {
                          $scope.openDetail = function (model, readOnly) {
                              $scope.$emit(EVENTS.wait);

                              if (angular.isUndefined($scope.list)) {
                                  var url = "views/" + $scope.templates.detail,
                                    model = model || {},
                                    modalInstance = null;

                                  if (angular.isDefined(model)) {
                                      $scope.model = model;
                                      model = model.convertToObject();
                                  }

                                  modalInstance = $uibModal.open({
                                      templateUrl: url,
                                      size: $scope.sizeDetail,
                                      resolve: {
                                          readOnly: function() {
                                              return readOnly ? readOnly : false;
                                          }
                                      },
                                      controller: ['$scope', '$validator', 'readOnly', function ($scopeD, $validator, readOnly) {
                                          try {
                                              $scope.$emit(EVENTS.ready);
                                              $scopeD.modelEdit = angular.copy(model);
                                              $scopeD.readOnly = readOnly;

                                              if (angular.isDefined($scope.model)) {
                                                  $scopeD.modelRead = $scope.manager.create();
                                                  $scopeD.modelRead.set(model);
                                              } else {
                                                  $scopeD.modelRead = {};
                                              }

                                              //Eventos
                                              $scopeD.onbeforesave = function (scopeD) {};
                                              $scopeD.onaftersave = function (scopeD) {};
                                              $scopeD.onbeforecancel = function (scopeD) {};
                                              $scopeD.onaftercancel = function (scopeD) {};

                                              if (angular.isDefined($scope.detailScope)) {
                                                  $scope.detailScope($scopeD, readOnly);
                                              }

                                              $scopeD.$watch('modelEdit', function (n, o) {
                                                  $scopeD.modelRead.set(n);
                                              }, true);

                                              $timeout(function () {
                                                  if (angular.toJson($scopeD.modelRead.getPrimaryKey()) !== angular.toJson($scope.manager.create().getPrimaryKey()))
                                                      $scopeD.title = $scopeD.editTitle;
                                                  else
                                                      $scopeD.title = $scopeD.addTitle;
                                              }, 100);

                                              if (angular.isUndefined($scope.saveEvent)) {
                                                  if ($scope.autoStore){
                                                      $scopeD.save = function () {
                                                          $scope.model.set($scopeD.modelEdit);
                                                          return $scope.manager.save($scope.model);
                                                      };
                                                  } else {
                                                      $scopeD.save = function () {
                                                          var defer = $q.defer();
                                                          $scope.model.set($scopeD.modelEdit);
                                                          defer.resolve([$scope.model]);
                                                          $scope.myData.push($scope.model);
                                                          return defer.promise;
                                                      };
                                                  }
                                              } else {
                                                  $scopeD.save = $scope.saveEvent;
                                              }

                                              save = $scopeD.save;

                                              $scopeD.save = function () {
                                                  return $validator.validate($scopeD).success(function () {
                                                      $scope.$emit(EVENTS.wait);
                                                      $scope.$emit(EVENTS.gridbeforesave, $scopeD);
                                                      $scopeD.onbeforesave($scopeD);

                                                      save().then(function (a) {
                                                          $scope.$emit(EVENTS.ready);
                                                          $scope.$emit(EVENTS.gridaftersave, $scopeD);
                                                          $scopeD.onaftersave($scopeD);
                                                          modalInstance.close();

                                                          if ($scope.autoStore) {
                                                              nS.success("Informação salva com sucesso!");
                                                              $scope.getPagedDataAsync($scope.gridOptions.pagingOptions.pageSize, $scope.gridOptions.pagingOptions.currentPage);
                                                          }
                                                      }, function (e) {
                                                          $scope.$emit(EVENTS.ready);
                                                          nS.error(e.message);
                                                      });
                                                  }).error(function () {

                                                  }).then(function () {

                                                  });
                                              };

                                              if (angular.isUndefined($scope.cancelEvent)) {
                                                  $scope.cancel = function () {
                                                      $scope.model = undefined;
                                                  };
                                              } else {
                                                  $scope.cancel = $scope.cancelEvent;
                                              }

                                              cancel = $scope.cancel;
                                              $scopeD.cancel = function () {
                                                  $scope.$emit(EVENTS.gridbeforecancel, $scopeD);
                                                  $scopeD.onbeforecancel($scopeD);
                                                  modalInstance.dismiss("cancel");
                                                  $scope.$emit(EVENTS.gridaftercancel, $scopeD);
                                                  $scopeD.onaftercancel($scopeD);
                                              };

                                              $scope.cancel = function () {
                                                  modalInstance.dismiss("cancel");
                                              };
                                          } catch (e) {
                                              nS.error(e.message);
                                          }
                                      }
                                      ]
                                  });

                                  modalInstance.result.then(function () {

                                  }, function () {
                                      cancel();
                                  });
                              } else {
                                  modalInstance = $uibModal.open({
                                      templateUrl: "views/" + $scope.templates.list,
                                      windowTemplateUrl: 'windowSelect.html',
                                      controller: $scope.list,
                                      size: 'lg'
                                  });

                                  $timeout(function () {
                                      var scopeM = jQuery('div[selectModel]:last-child').isolateScope(),
                                        scope = jQuery('div[selectModel]:last-child').find('div[modal-transclude]>.ng-scope').scope();

                                      scopeM.select = function () {
                                          modalInstance.close(scope.selections);
                                      };

                                      scopeM.cancel = function () {
                                          modalInstance.dismiss("cancel");
                                      };

                                      $scope.$emit(EVENTS.ready);
                                  }, 100);

                                  modalInstance.result.then((function (result) {
                                      for (var k in result) {
                                          var exists = false;

                                          for (var k2 in $scope.myData) {
                                              if (result[k] === $scope.myData[k2]) {
                                                  exists = true;
                                                  break;
                                              }
                                          }

                                          if (!exists){
                                              $scope.myData.push(result[k]);
                                          }
                                      }
                                  }), function () {

                                  });
                              }
                          };
                      }
                  } else {
                      $scope.openDetail = $scope.addEvent;
                  }

                  $scope.$emit(EVENTS.gridready, $scope);
                  $scope.searchGrid();
              }
          ]
      };
  }]);

angular.module("Generic")
  .run(['$templateCache', '$http', function ($templateCache, $http) {
      $http.get("views/generic/common/templates/searchTemplate.html").then(function (result) {
          $templateCache.put("searchTemplate.html", result.data);
      });

      $http.get("views/generic/common/templates/gridTemplate.html").then(function (result) {
          $templateCache.put("gridTemplate.html", result.data);
      });

      $templateCache.put('menuTemplate.html',
        "<div ng-show=\"showColumnMenu\"  class=\"ngHeaderButton\" ng-click=\"toggleShowMenu()\">\r" +
        "\n" +
        "    <div class=\"ngHeaderButtonArrow\"></div>\r" +
        "\n" +
        "</div>\r" +
        "\n" +
        "<div ng-show=\"showMenu\" class=\"ngColMenu\">\r" +
        "\n" +
        "    <div ng-show=\"showColumnMenu\">\r" +
        "\n" +
        "        <span class=\"ngMenuText\">{{i18n.ngMenuText}}</span>\r" +
        "\n" +
        "        <ul class=\"ngColList\">\r" +
        "\n" +
        "            <li class=\"ngColListItem\" ng-repeat=\"col in columns | ngColumns\">\r" +
        "\n" +
        "                <label><input ng-disabled=\"col.pinned\" type=\"checkbox\" class=\"ngColListCheckbox\" ng-model=\"col.visible\"/>{{col.displayName}}</label>\r" +
        "\n" +
        "\t\t\t\t<a title=\"Group By\" ng-class=\"col.groupedByClass()\" ng-show=\"col.groupable && col.visible\" ng-click=\"groupBy(col)\"></a>\r" +
        "\n" +
        "\t\t\t\t<span class=\"ngGroupingNumber\" ng-show=\"col.groupIndex > 0\">{{col.groupIndex}}</span>          \r" +
        "\n" +
        "            </li>\r" +
        "\n" +
        "        </ul>\r" +
        "\n" +
        "    </div>\r" +
        "\n" +
        "</div>"
      );

      $http.get("views/generic/common/templates/footerTemplate.html").then(function (result) {
          $templateCache.put("footerTemplate.html", result.data);
      });

      $http.get("views/generic/common/templates/rowTemplate.html").then(function (result) {
          $templateCache.put("rowTemplate.html", result.data);
      });
  }]);