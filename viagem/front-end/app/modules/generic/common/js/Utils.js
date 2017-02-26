
"use strict";

angular.extend(angular, function(){
    
  function buildParams(prefix, obj, add) {
    var name, i, l, rbracket;
    rbracket = /\[\]$/;
    if (obj instanceof Array) {
      for (i = 0, l = obj.length; i < l; i++) {
        if (rbracket.test(prefix)) {
          add(prefix, obj[i]);
        } else {
          buildParams(prefix + "[" + ( typeof obj[i] === "object" ? i : "" ) + "]", obj[i], add);
        }
      }
    } else if (typeof obj === "object") {
      // Serialize object item.
      for (name in obj) {
        buildParams(prefix + "[" + name + "]", obj[ name ], add);
      }
    } else {
      // Serialize scalar item.
      add(prefix, obj);
    }
  };
    
  return {
    toParam: function (a) {
      var prefix, s, add, name, r20, output;
      s = [];
      r20 = /%20/g;
      add = function (key, value) {
        // If value is a function, invoke it and return its value
        value = ( typeof value === 'function' ) ? value() : ( value === null ? "" : value );
        s[ s.length ] = encodeURIComponent(key) + "=" + encodeURIComponent(value);
      };
      if (a instanceof Array) {
        for (name in a) {
          add(name, a[name]);
        }
      } else {
        for (prefix in a) {
          buildParams(prefix, a[ prefix ], add);
        }
      }
      output = s.join("&").replace(r20, "+");
      return output;
    },
    navigateObject: function(object, path){
      
      if(angular.isObject(object) && angular.isString(path)){
        var div = path.split('.');
        for(var i in div){
          if(angular.isDefined(object[div[i]]))
            object = object[div[i]];
          else
            return undefined;
        }
        return object;
      }
      
      return undefined;
      
    }
  };
}());

angular.module('Generic')
  .factory('utils', function(){
    var utils;
    utils = {
      convertHyphenUpperCase: function(v){
        var blocks = v.split("-");
        v = "";
        for(var k in blocks){
          v += blocks[k].charAt(0).toUpperCase() + blocks[k].slice(1);
        }
        return v;
      }
    };
    return utils;
  });