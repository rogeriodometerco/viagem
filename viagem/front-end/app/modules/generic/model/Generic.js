"use strict";

angular.module('Generic')
    .factory("Generic.Model.Generic", ['$injector', '$q', function($injector, $q){
            
    function GenericModel(){
    };

    GenericModel.__asMethod__ = function(func, superClass) {  
      return function() {
        var currentSuperClass = this.$;
        this.$ = superClass;
        var ret = func.apply(this, arguments);      
        this.$ = currentSuperClass;
        return ret;
      };
    };
 
    GenericModel.extend = function(className, def) {
          
      var old = this.prototype,
          fields = {},
          fieldsName = {};
      
      function classDef(){
        this.$resource = {
          baseUrl: '',
          dataDefault: {},
          api: {
            load: {
              method  : 'get',
              url     : ''
            },
            save: {
              method  : 'post',
              url     : ''
            },
            update: {
              method  : 'put',
              url     : ''
            },
            remove: {
              method  : 'delete',
              url     : ''
            }
          }
        };
        this.$data          = {};
        this.$fieldsName    = {};
        this.$primaryKey    = [];
        this.$associations  = [];
        this.construct.apply(this, arguments);
      };
      
      classDef.prototype.$ = this.prototype;
      angular.extend(classDef.prototype, this.prototype);
      angular.extend(classDef.prototype, def);
      classDef.prototype.$className = className;
      classDef.extend = GenericModel.extend;
      
      if(angular.isDefined(old.fields) && angular.isDefined(classDef.prototype.fields)){
        classDef.prototype.fields = classDef.prototype.fields.concat(old.fields);
      }
      classDef.prototype.fields.forEach(function(v, k){
        fields[v.name] = v;
        fieldsName[v.field ? v.field : v.name] = v;
      });
      classDef.prototype.fields = fields;
      classDef.prototype.fieldsName = fieldsName;
      
      return classDef;
    };

    GenericModel.prototype = {
      $val: function(property, value){
        var field = null;                      
        
        if(angular.isDefined(this.$data[property]))
          field = [property];
        else if(angular.isDefined(this.$fieldsName[property]))
          field = this.$fieldsName[property];
        else
          throw new Error("O campo \""+property+"\" não existe no modelo "+this.$className);
        
        for(var f in field){
          f = field[f];
          if(angular.isDefined(this.$associations[f])){
            var ass = this.$associations[f];
            if(angular.isDefined(ass.association.model)){
              this.$doOneToOne(ass);
              if(angular.isObject(value))
                  this.$data[f].set(value);
            }else if(angular.isDefined(ass.association.manager)){
              if(angular.isArray(value)){
                this.$associationArrays[f] = value;
                this.$data[f].clear();
                this.$data[f].add(value);
              }else if(angular.isObject(value) && angular.isDefined(value.getCollection)){
                this.$associationArrays[f] = value.getCollection();
                this.$data[f] = value;
              }
            }
          }else{
            this.$data[f] = this.$convertType(f, angular.isDefined(this.fields[f].set) ? this.fields[f].set(value, this) : value);
          }
        }
      },
      $convertType: function(property, value){
        if(angular.isDefined(this.fields[property].type)){
          if(this.fields[property].type === "number"){
            return Number(value);
          }else if(this.fields[property].type === "string"){
            return String(value);
          }else if(this.fields[property].type === "datetime"){
            return new Date(value);
          }else if(this.fields[property].type === "boolean"){
            if(angular.isNumber(value)){
              return value === 0 ? false : true;
            }else if(angular.isString(value)){
              return value === 'F' ? false : true;
            }else{
              return value;
            }
          }
        }else
          return value;
      },
      $prepareData: function(action){
        var dataRest    = {},
            k = '';
        
        if(this.$primaryKey.length <= 0)
          throw new Error("Nenhuma chave primária especificada no modelo "+this.getTypeName());
        
        if(action !== "save")
          for(k in this.$primaryKey)
            if(this.$data[this.$primaryKey[k]] === "" || this.$data[this.$primaryKey[k]] === null)
              throw new Error("Chave primária \""+this.$primaryKey[k]+"\" do modelo "+this.getTypeName()+" está vazia");
            
        
        angular.extend(dataRest, this.resource.dataDefault);
        dataRest.data   = this.convertToObject();
        
        for(k in this.$associationArrays){
          var asso = this.$associationArrays[k];
          for(var k2 in asso){
            if(angular.isDefined(asso[k2].convertToObject))
              asso[k2] = asso[k2].convertToObject();
          }
          dataRest.data[k] = asso;
        }
        
        return dataRest;
      },
      $doOneToOne: function(field){
        try{
          var model = $injector.get(field.association.model);
          this.$data[field.name] = new model;
        }catch(e){
          if(e.message.indexOf('Unknown provider') > -1)
            throw new Error('O modelo '+this.getTypeName()+' não encontrou o modelo '+field.association.model);
        }
      },
      $doMany: function(field){
          
        try{
            
          var source      = this,
              manager     = $injector.get(field.association.manager),
              getterName  = '';
          
          manager = new manager;
          angular.extend(manager.$resource, field.association.resource);
          this.$data[field.name] = manager;
          
          if(angular.isDefined(field.association.getterName))
            getterName  = field.association.getterName;
          else
            getterName  = field.name+"s";
          
          this[getterName] = function(){

            var filter  = [];
            
            for(var k in field.association.key){
              var key = field.association.key[k];
              filter[0] = {
                field: key.foreing,
                value: source.$data[key.field],
                condition: '='
              };
            }
            
            source.$data[field.name].filter(filter);
            
            return source.$data[field.name];
              
          };
            
        }catch(e){
          if(e.message.indexOf('Unknown provider') > -1)
            throw new Error('O modelo '+this.getTypeName()+' não encontrou o gerenciador '+field.association.manager);
          else if(e.message.indexOf('unpr') > -1)
            throw new Error('O modelo '+this.getTypeName()+' não encontrou o gerenciador '+field.association.manager);
        }
      },
      $populate: function(property, value, origin){
        
        if(property instanceof Object){
          var obj = {};
          angular.copy(property, obj);
          for(var p in obj) {
            this.$populate(p, obj[p], origin);
          }
        }else{
          this.$val(property, value);
        }
        
      },
      $convertObjectToFields: function(data) {
        var me    = this,
            obj   = {},
            model = {};
        
        if(angular.isString(data)){
          var div = data.split('.');
          if(div.length === 1)
            return me.fields[data].field ? me.fields[data].field : data;
          else{
            obj = me;
            obj = me.fields[div[0]];
            if(angular.isDefined(obj.association)){
              if(angular.isDefined(obj.association.model)){
                model = $injector.get(obj.association.model);
                obj = model.prototype.$convertObjectToFields(data.replace(div[0]+".", ''));
              }
            }
            return div[0]+'.'+obj;
          }
        }else if(!angular.isArray(data)){
          $.each(data, function(k, v){
            if(angular.isDefined(me.fields[k])){
              if(angular.isDefined(me.fields[k].association)){
                if(angular.isDefined(me.fields[k].association.model)){
                  model = $injector.get(me.fields[k].association.model);
                  obj[me.fields[k].field ? me.fields[k].field : k] = model.prototype.$convertObjectToFields(data[k]);
                }else if(angular.isDefined(me.fields[k].association.manager)){
                  obj[me.fields[k].field ? me.fields[k].field : k] = data[k];
                }
              }else
                obj[me.fields[k].field ? me.fields[k].field : k] = data[k];
            }else
              obj[k] = data[k];
          });
          
          return obj;
          
        }
        
        return data;
        
      },
      construct: function(){
        //Primary Keys
        for(var k in this.fields){
          if(angular.isUndefined(this.$fieldsName[this.fields[k].field])){
          this.$data[this.fields[k].name] = '';
          this.fields[this.fields[k].name] = this.fields[k];
          if(this.fields[k].field){
            if(!angular.isArray(this.$fieldsName[this.fields[k].field]))
              this.$fieldsName[this.fields[k].field] = new Array;
            this.$fieldsName[this.fields[k].field].push(this.fields[k].name);
          }
          if(this.fields[k].primaryKey)
            this.$primaryKey.push(this.fields[k].name);
          }
        }

        //Resource
        this.$resource.baseUrl = this.resource.baseUrl;
        if(angular.isDefined(this.resource.api)){
          angular.extend(this.$resource.api.load, this.resource.api.load);
          angular.extend(this.$resource.api.save, this.resource.api.save);
          angular.extend(this.$resource.api.update, this.resource.api.update);
          angular.extend(this.$resource.api.remove, this.resource.api.remove);
        }

        //Association
        for(var field in this.fields){
          var fieldName = field;
          field         = this.fields[field];
          if(angular.isDefined(field.association)){
            if(angular.isObject(field.association))
              this.$associations[fieldName] = field;
              if(angular.isDefined(field.association.manager)){
                this.$doMany(field);
              }
            else if(angular.isString(field.association)){
              field.association = {
                model: field.association
              };
              field.name = fieldName;
              this.$associations[fieldName] = field;
            }
          }
        }
        this.$associationArrays = [];
        
        for(var f in this.$data){
          if(this.$data[f] === '')
            this.$data[f] = null;
        }
        
      },
      set: function(property, value){
        this.$populate(property, value, property);
        return this;
      },
      get: function(property){
        if(angular.isUndefined(this.$data[property]))
          throw new Error("O campo \""+property+"\" não existe no modelo "+this.$className); 
        return this.$data[property];                    
      },
      getPrimaryKey: function(){
        var keys = {};
        for(var k in this.$primaryKey)
          keys[this.$primaryKey[k]] = this.get(this.$primaryKey[k]);
        return keys;
      },
      getTypeName: function(){
        return this.$className;
      },
      convertObjectToFields: function() {
        return this.$convertObjectToFields(this.convertToObject(true));
      },
      convertToObject: function(fields, recursive){
        var model     = this,
            data      = {},
            recursive = recursive === false ? false : true;
        for(var k in model.$data){
          if(model.$data[k] != null){
            if(angular.isDefined(model.$data[k].getTypeName) && recursive)
              data[k] = fields ? model.$data[k].convertObjectToFields() : model.$data[k].convertToObject();
            else if(angular.isDefined(model.$data[k].$pool) && recursive)
              data[k] = model.$data[k].convertToArray(fields);
            else
              data[k] = model.$data[k];
          }
        }
        return data;
      }
    };
    
    return GenericModel;      
  }]);