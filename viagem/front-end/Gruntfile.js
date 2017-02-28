// Generated on 2015-10-03 using
// generator-webapp 1.0.1
'use strict';

// # Globbing
// for performance reasons we're only matching one level down:
// 'test/spec/{,*/}*.js'
// If you want to recursively match all subfolders, use:
// 'test/spec/**/*.js'

module.exports = function (grunt) {

  // Time how long tasks take. Can help when optimizing build times
  require('time-grunt')(grunt);
  
  // Automatically load required grunt tasks
  require('jit-grunt')(grunt, {
    useminPrepare: 'grunt-usemin'
  });

  grunt.loadNpmTasks('grunt-ftp-upload');
  grunt.loadNpmTasks('grunt-http');
  grunt.loadNpmTasks('grunt-wait');
  grunt.loadNpmTasks('grunt-include-source');

  //Manifest
  grunt.loadNpmTasks('grunt-appcache');

  // Configurable paths
  var config = {
    app: 'app',
    dist: 'dist'
  };

  // Define the configuration for all the tasks
  grunt.initConfig({

    pkg: grunt.file.readJSON('package.json'),

    // Project settings
    config: config,

    // Watches files for changes and runs tasks based on the changed files
    watch: {
      bower: {
        files: ['bower.json'],
        tasks: ['wiredep']
      },
      babel: {
        files: ['<%= config.app %>/scripts/{,*/}*.js'],
        tasks: ['babel:dist']
      },
      babelTest: {
        files: ['test/spec/{,*/}*.js'],
        tasks: ['babel:test', 'test:watch']
      },
      gruntfile: {
        files: ['Gruntfile.js']
      },
      sass: {
        files: ['<%= config.app %>/styles/{,*/}*.{scss,sass}', '<%= config.app %>/common/styles/{,*/}*.{scss,sass}'],
        tasks: ['sass:server', 'postcss']
      },
      styles: {
        files: ['<%= config.app %>/styles/{,*/}*.css', '<%= config.app %>/common/styles/{,*/}*.css'],
        tasks: ['newer:copy:styles', 'postcss']
      }
    },

    browserSync: {
      options: {
        notify: false,
        background: true
      },
      livereload: {
        options: {
          files: [
            '<%= config.app %>/**/*.html',
            '<%= config.app %>/dentalfoz.appcache',
            '.tmp/styles/{,*/}*.css',
            '.tmp/common/styles/{,*/}*.css',
            '<%= config.app %>/images/{,*/}*',
            '.tmp/scripts/{,*/}*.js'
          ],
          port: 8082,
          server: {
            baseDir: ['.tmp', config.app],
            routes: {
              '/bower_components': './bower_components',
              '/login': 'app/index.html',
              '/pais': 'app/index.html',
              '/dashboard': 'app/index.html',
              '/localizacao/pais.edit': 'app/index.html',
              '/localizacao/pais.list': 'app/index.html',
              '/localizacao/estado.edit': 'app/index.html',
              '/localizacao/estado.list': 'app/index.html',
              '/localizacao/cidade.edit': 'app/index.html',
              '/localizacao/cidade.list': 'app/index.html'
            }
          }
        }
      },
      test: {
        options: {
          port: 8081,
          open: false,
          logLevel: 'silent',
          host: 'localhost',
          server: {
            baseDir: ['.tmp', './test', config.app],
            routes: {
              '/bower_components': './bower_components'
            }
          }
        }
      },
      dist: {
        options: {
          background: false,
          server: '<%= config.dist %>'
        }
      }
    },

    // Empties folders to start fresh
    clean: {
      dist: {
        files: [{
          dot: true,
          src: [
            '.tmp',
            '<%= config.dist %>/*',
            '!<%= config.dist %>/.git*'
          ]
        }]
      },
      server: '.tmp'
    },

    // Make sure code styles are up to par and there are no obvious mistakes
    eslint: {
      target: [
        'Gruntfile.js',
        '<%= config.app %>/scripts/{,*/}*.js',
        '!<%= config.app %>/scripts/vendor/*',
        'test/spec/{,*/}*.js'
      ]
    },

    // Mocha testing framework configuration options
    mocha: {
      all: {
        options: {
          run: true,
          urls: ['http://<%= browserSync.test.options.host %>:<%= browserSync.test.options.port %>/index.html']
        }
      }
    },

    // Compiles ES6 with Babel
    babel: {
      options: {
        sourceMap: true
      },
      dist: {
        files: [{
          expand: true,
          cwd: '<%= config.app %>/scripts',
          src: '{,*/}*.js',
          dest: '.tmp/scripts',
          ext: '.js'
        }]
      },
      test: {
        files: [{
          expand: true,
          cwd: 'test/spec',
          src: '{,*/}*.js',
          dest: '.tmp/spec',
          ext: '.js'
        }]
      }
    },

    // Compiles Sass to CSS and generates necessary files if requested
    sass: {
      options: {
        sourceMap: true,
        sourceMapEmbed: true,
        sourceMapContents: true,
        includePaths: ['.']
      },
      dist: {
        files: [{
          expand: true,
          cwd: '<%= config.app %>/styles',
          src: ['*.{scss,sass}'],
          dest: '.tmp/styles',
          ext: '.css'
        },{
          expand: true,
          cwd: '<%= config.app %>/common/styles',
          src: ['*.{scss,sass}'],
          dest: '.tmp/common/styles',
          ext: '.css'
        }]
      },
      server: {
        files: [{
          expand: true,
          cwd: '<%= config.app %>/styles',
          src: ['*.{scss,sass}'],
          dest: '.tmp/styles',
          ext: '.css'
        },{
          expand: true,
          cwd: '<%= config.app %>/common/styles',
          src: ['*.{scss,sass}'],
          dest: '.tmp/common/styles',
          ext: '.css'
        }]
      }
    },

    postcss: {
      options: {
        map: true,
        processors: [
          // Add vendor prefixed styles
          require('autoprefixer-core')({
            browsers: ['> 1%', 'last 2 versions', 'Firefox ESR', 'Opera 12.1']
          })
        ]
      },
      dist: {
        files: [{
          expand: true,
          cwd: '.tmp/styles/',
          src: '{,*/}*.css',
          dest: '.tmp/styles/'
        },{
          expand: true,
          cwd: '.tmp/common/styles/',
          src: '{,*/}*.css',
          dest: '.tmp/common/styles/'
        }]
      }
    },

    // Automatically inject Bower components into the HTML file
    wiredep: {
      app: {
        src: ['<%= config.app %>/index.html'],
        exclude: ['bootstrap.js'],
        ignorePath: /^(\.\.\/)*\.\./,
        /*fileTypes: {
         html: {
         replace: {
         js: '<script defer src="{{filePath}}"></script>'
         }
         }
         }*/
      },
      sass: {
        src: ['<%= config.app %>/styles/{,*/}*.{scss,sass}','<%= config.app %>/common/styles/{,*/}*.{scss,sass}'],
        ignorePath: /^(\.\.\/)+/
      }
    },

    // Renames files for browser caching purposes
    filerev: {
      dist: {
        src: [
          '<%= config.dist %>/js/{,*/}*.js',
          '<%= config.dist %>/styles/{,*/}*.css',
          '<%= config.dist %>/common/styles/{,*/}*.css',
          '<%= config.dist %>/images/{,*/}*.*',
          '<%= config.dist %>/styles/fonts/{,*/}*.*',
          '<%= config.dist %>/*.{ico,png}'
        ]
      }
    },

    // Reads HTML for usemin blocks to enable smart builds that automatically
    // concat, minify and revision files. Creates configurations in memory so
    // additional tasks can operate on them
    useminPrepare: {
      options: {
        dest: '<%= config.dist %>'
      },
      html: '<%= config.app %>/index.html'
    },

    // Performs rewrites based on rev and the useminPrepare configuration
    usemin: {
      options: {
        assetsDirs: [
          '<%= config.dist %>',
          '<%= config.dist %>/images',
          '<%= config.dist %>/styles',
          '<%= config.dist %>/common/styles'
        ]
      },
      html: ['<%= config.dist %>/**/*.html'],
      css: ['<%= config.dist %>/styles/{,*/}*.css', '<%= config.dist %>/common/styles/{,*/}*.css']
    },

    // The following *-min tasks produce minified files in the dist folder
    imagemin: {
      dist: {
        files: [{
          expand: true,
          cwd: '<%= config.app %>/images',
          src: '{,*/}*.{gif,jpeg,jpg,png}',
          dest: '<%= config.dist %>/images'
        }]
      }
    },

    svgmin: {
      dist: {
        files: [{
          expand: true,
          cwd: '<%= config.app %>/images',
          src: '{,*/}*.svg',
          dest: '<%= config.dist %>/images'
        }]
      }
    },

    htmlmin: {
      dist: {
        options: {
          collapseBooleanAttributes: true,
          collapseWhitespace: true,
          conservativeCollapse: true,
          removeAttributeQuotes: true,
          removeCommentsFromCDATA: true,
          removeEmptyAttributes: true,
          removeOptionalTags: true,
          // true would impact styles with attribute selectors
          removeRedundantAttributes: false,
          useShortDoctype: true
        },
        files: [{
          expand: true,
          cwd: '<%= config.dist %>',
          src: '{,**/*}*.html',
          dest: '<%= config.dist %>'
        }]
      }
    },

    // By default, your `index.html`'s <!-- Usemin block --> will take care
    // of minification. These next options are pre-configured if you do not
    // wish to use the Usemin blocks.
    // cssmin: {
    //   dist: {
    //     files: {
    //       '<%= config.dist %>/styles/main.css': [
    //         '.tmp/styles/{,*/}*.css',
    //         '<%= config.app %>/styles/{,*/}*.css'
    //       ]
    //     }
    //   }
    // },
    // uglify: {
    //   dist: {
    //     files: {
    //       '<%= config.dist %>/scripts/scripts.js': [
    //         '<%= config.dist %>/scripts/scripts.js'
    //       ]
    //     }
    //   }
    // },
    // concat: {
    //   dist: {}
    // },

    // Copies remaining files to places other tasks can use
    copy: {
      dist: {
        files: [{
          expand: true,
          dot: true,
          cwd: '<%= config.app %>',
          dest: '<%= config.dist %>',
          src: [
            '*.{ico,png,txt}',
            'images/{,*/}*.webp',
            '**/*.html',
            'fonts/{,*/}*.*',
            'styles/{,*/}*.css',
            'common/styles/{,*/}*.css',
            'common/img/{,*/}*',
            'common/fonts/{,*/}*',
            'common/js//plugins/{,*/}*',
            'scripts/magiczoomplus.js'
          ]
        },{
          expand: true,
          dot: true,
          cwd: '.tmp/concat',
          dest: '<%= config.dist %>',
          src: [
            '**/*.js'
          ]
        }, {
          expand: true,
          dot: false,
          cwd: '.',
          src: 'bower_components/bootstrap-sass/assets/fonts/bootstrap/*',
          dest: '<%= config.dist %>'
        }, {
          expand: true,
          dot: true,
          flatten: true,
          filter: 'isFile',
          src: 'bower_components/font-awesome/fonts/*',
          dest: '<%= config.dist %>/fonts/'
        }]
      },
      index: {
        files: [{
          src: '<%= config.dist %>/index.html',
          dest: '<%= config.dist %>/index.phtml'
        }]
      }
    },

    // Generates a custom Modernizr build that includes only the tests you
    // reference in your app
    modernizr: {
      dist: {
        devFile: 'bower_components/modernizr/modernizr.js',
        outputFile: '<%= config.dist %>/scripts/vendor/modernizr.js',
        files: {
          src: [
            '<%= config.dist %>/scripts/{,*/}*.js',
            '<%= config.dist %>/styles/{,*/}*.css',
            '<%= config.dist %>/common/styles/{,*/}*.css',
            '!<%= config.dist %>/scripts/vendor/*'
          ]
        },
        uglify: true
      }
    },

    // Run some tasks in parallel to speed up build process
    concurrent: {
      server: [
        'babel:dist',
        'sass:server'
      ],
      test: [
        'babel'
      ],
      dist: [
        'babel',
        'sass',
        'imagemin',
        'svgmin'
      ]
    },

    'ftp_upload': {
      'build-public': {
        auth: {
          host: '',
          port: 21,
          authKey: 'key1'
        },
        src: 'dist',
        dest: '/sistema',
        exclusions: ['dist/**/.DS_Store', 'dist/**/Thumbs.db', 'dist/index.*'],
        //exclusions: ['dist/**/.DS_Store', 'dist/**/Thumbs.db']
      },
      'build-index': {
        auth: {
          host: 'lubro.com.br',
          port: 21,
          authKey: 'key1'
        },
        src: ['dist/index.phtml'],
        dest: '/module/Application/view/layout'
      }
    },

    http: {
      'clear-cache': {
        options: {
          url: ''
        }
      },
      'home': {
        options: {
          url: ''
        }
      }
    },

    wait: {
      wait: {
        options: {
          delay: 1000
        }
      }
    },

    appcache: {
      options: {
        // appcache is always for the distrib version not for development
        basePath: '<%= config.dist %>',
      },
      // this target is only for application
      dist: {
        // we are now in the root of the web app
        baseUrl: '/',
        // appcache is always for the distrib version not for development
        dest: '<%= config.dist %>/gestorrural.appcache',
        cache: {
          patterns: [
            // add all css, js and assets of my application
            '<%= config.dist %>/**/*',
            '!<%= config.dist %>/index.html',
            '!<%= config.dist %>/index.phtml'
          ],
          // and finish to add components which haven't a partial appcache
          //pageslinks: '<%= config.dist %>/index.html'
        },
        network: '*',
        //fallback: '/ /offline.html'
      }
    },
    
    includeSource: {
      options: {
        basePath: 'app',
        baseUrl: '/',
        ordering: 'top-down',
        rename: function(dest, matchedSrcPath, options) {
          if(matchedSrcPath.indexOf('.scss') === -1)
            return dest+matchedSrcPath;
          else
            return dest+matchedSrcPath.replace('.scss', '.css');
        }
      },
      myTarget: {
        files: {
          'app/index.html': 'app/index.html'
        }
      }
    }

  });

  grunt.registerTask('manifest', ['appcache:dist']);
  grunt.registerTask('inject', ['includeSource']);
  grunt.registerTask('serve', 'start the server and preview your app', function (target) {

    if (target === 'dist') {
      return grunt.task.run(['build', 'browserSync:dist']);
    }

    grunt.task.run([
      'clean:server',
      'includeSource',
      'wiredep',
      'concurrent:server',
      'postcss',
      'browserSync:livereload',
      'watch'
    ]);
  });

  grunt.registerTask('server', function (target) {
    grunt.log.warn('The `server` task has been deprecated. Use `grunt serve` to start a server.');
    grunt.task.run([target ? ('serve:' + target) : 'serve']);
  });

  grunt.registerTask('test', function (target) {
    if (target !== 'watch') {
      grunt.task.run([
        'clean:server',
        'concurrent:test',
        'postcss'
      ]);
    }

    grunt.task.run([
      'browserSync:test',
      'mocha'
    ]);
  });

  grunt.registerTask('build', [
    'clean:dist',
    'wiredep',
    'useminPrepare',
    'concurrent:dist',
    'postcss',
    'concat',
    'cssmin',
    //'uglify',
    'copy:dist',
    //'modernizr',
    'filerev',
    'usemin',
    'htmlmin',
    //'appcache:dist'
  ]);

  grunt.registerTask('deploy', [
    //'copy:index',
    'ftp_upload:build-public',
    //'http:clear-cache',
    //'wait',
    //'http:home'
  ]);

  grunt.registerTask('default', [
    'newer:eslint',
    'test',
    'build'
  ]);
};