"use strict";

angular.module(APP_NAME)
    .provider("$database", [
        function () {
            var dbname;
            var dbversion;
            var bootstrapConfig;
            var dbInstance;

            var getInstance = function (callbackFn) {
                if (dbInstance) {
                    callbackFn(dbInstance);
                } else {
                    var dbRequest = window.indexedDB.open(dbname, dbversion);

                    dbRequest.onupgradeneeded = function (event) {
                        dbInstance = event.target.result;
                        bootstrapConfig(event, dbInstance);
                    };

                    dbRequest.onblocked = function (event) {
                        throw window.Coamo.CustomError("Erro ao abrir banco de dados indexedDB");
                    };

                    dbRequest.onerror = function (event) {
                        throw window.Coamo.CustomError("Erro ao abrir banco de dados indexedDB");
                    };

                    dbRequest.onsuccess = function (event) {
                        dbInstance = event.target.result;
                        callbackFn(dbInstance);
                    };
                }
            };

            return {
                dbname: function (value) {
                    dbname = value;
                },
                dbversion: function (value) {
                    dbversion = value;
                },
                bootstrapConfig: function (bootstrapFn) {
                    bootstrapConfig = bootstrapFn;
                },
                deleteDatabase: function () {
                    window.indexedDB.deleteDatabase(dbname);
                },
                upDatabase: function () {
                    getInstance(function (db) {

                    });
                },
                $get: function () {
                    return {
                        objectStores: function (collections, callbackFn) {
                            getInstance(function (db) {
                                callbackFn(db.transaction(collections, "readwrite"));
                            });
                        },
                        objectStore: function (collection, callbackFn) {
                            getInstance(function (db) {
                                var transaction = db.transaction([collection], "readwrite");
                                var objectStore = transaction.objectStore(collection);

                                callbackFn(objectStore, transaction);
                            });
                        }
                    };
                }
            };
        }
    ]);