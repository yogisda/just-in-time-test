const { task, dest, watch, src, series, parallel } = require('gulp');
const del = require('del');
const sass = require('gulp-sass');
const concat = require('gulp-concat');
const concatCss = require('gulp-concat-css');
const minifyCss = require('gulp-clean-css');
const uglify = require('gulp-uglify');
const rename = require('gulp-rename');
const pug = require('gulp-pug');
const autoprefixer = require('gulp-autoprefixer');
const sourcemaps = require('gulp-sourcemaps');
const merge = require('merge-stream');
const browserSync = require('browser-sync');
const replace = require('gulp-token-replace');
const headerComment = require('gulp-header-comment');
const gulpif = require('gulp-if');
const source = require('vinyl-source-stream');
const buffer = require('vinyl-buffer');
const ts = require('gulp-typescript');
const tsProject = ts.createProject('tsconfig.json');
const tsify = require("tsify");
const browserify = require('browserify');
// const watchify = require('watchify');
const babelify = require('babelify');

const server = browserSync.create();

const options = {
  npmPath: 'node_modules/',
  externalSeverRoot: '/web/app',
  devServerRoot: 'dist',
  headerText: `Build: <%= moment().format('YYYYMMDD') %>-<%= Math.random().toString(36).toUpperCase().substr(2, 10) %>-<%= moment().format('hhmmss') %>
                Version: <%= pkg.version %>
                Copyright (c) <%= moment().format('YYYY') %> Just-In-Time`,
};

let sassBundles = [
  {
    sassFiles: [
      options.npmPath + 'normalize.css/normalize.css',
      'scss/global.scss',
    ],
    outputFile: 'main.css',
    outputFolder: options.devServerRoot + '/css',
  },
];
let watchSassFiles = ['scss/**/*.scss'];

let watchJSFiles = ['js/**/*.ts'];

let pugBundles = [
  {
    pugFiles: ['pug/sites/**/*.pug'],
    outputFile: 'index.html',
    outputFolder: options.devServerRoot + '/',
  },
];
let watchPugFiles = ['pug/**/*.pug'];

let fontsBundle = {
  fontFiles: ['fonts/*'],
};

function reload(done) {
  server.reload();
  done();
}

function serve(done) {
  server.init({
    server: {
      baseDir: options.devServerRoot,
    },
  });
  done();
}

function clean() {
  return del([options.devServerRoot]);
}

function scss() {
  let bundles = [];
  sassBundles.forEach(function(bundle, index) {
    bundles[index] = src(bundle.sassFiles)
      .pipe(sass( {
        includePaths:['node_modules'],
      }).on('error', sass.logError))
      .pipe(
        concatCss(bundle.outputFile, {
          rebaseUrls: false,
        })
      )
      // Base64 is super slow. Use only if necessary
      // .pipe(cssBase64())
      .pipe(autoprefixer())
      .pipe(headerComment(options.headerText))
      .pipe(dest(bundle.outputFolder))
      .pipe(sourcemaps.init())
      .pipe(minifyCss())
      .pipe(
        rename({
          extname: '.min.css',
        })
      )
      .pipe(headerComment(options.headerText))
      .pipe(sourcemaps.write(''))
      .pipe(dest(bundle.outputFolder));
  });
  return merge(bundles);
}

function js() {
  return browserify({
    entries: ['./js/main.ts'],
    debug: true,
    cache: {},
    packageCache: {},
  })
    .plugin(tsify)
    .bundle()
    .on('error', function(error) {
      console.error(error.message);
      this.emit('end');
    })
    .pipe(source('main.js'))
    .pipe(buffer())
    .pipe(headerComment(options.headerText))
    .pipe(dest(options.devServerRoot + '/js'))
    .pipe(
      rename({
        extname: '.min.js',
      })
    )
    .pipe(
      sourcemaps.init({
        loadMaps: true,
      })
    )
    .pipe(uglify())
    .pipe(headerComment(options.headerText))
    .pipe(sourcemaps.write('.'))
    .pipe(dest(options.devServerRoot + '/js'))
    .pipe(server.stream());
}

function pugBuild() {
  let bundles = [];
  pugBundles.forEach(function(bundle, index) {
    bundles[index] = src(bundle.pugFiles)
      .pipe(
        pug({
          pretty: true,
        }).on('error', function(err) {
          console.error(err.message);
          this.emit('end');
        })
      )
      .pipe(dest(bundle.outputFolder));
  });

  return merge(bundles);
}

function favicon() {
  return src('images/favicon/*').pipe(dest(options.devServerRoot + '/'));
}

const images = parallel(favicon, function copyImages() {
  return src('images/**/*').pipe(dest(options.devServerRoot + '/images/'));
});

function fonts() {
  return src(fontsBundle.fontFiles).pipe(
    dest(options.devServerRoot + '/fonts/')
  );
}

const watcher = parallel(
  function watcherPug() {
    return watch(watchPugFiles, series(pugBuild, reload));
  },
  function watcherScss() {
    return watch(watchSassFiles, series(scss, reload));
  },
  function watcherJS() {
    return watch(watchJSFiles, series(js, reload));
  }
);

const build = series(clean, parallel(scss, js, pugBuild, images, fonts));

// const dev = series(build, serve, reload);
const dev = series(build, serve, watcher, reload);


function errorHandler(error) {
  console.log(error.message);
}

exports.default = dev;
