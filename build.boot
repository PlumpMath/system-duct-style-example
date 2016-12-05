(set-env!
 :source-paths   #{"src"}
 :resource-paths #{"resources"}
 :dependencies '[[adzerk/boot-reload    "0.4.13"      :scope "test"]
                 [org.clojure/clojure "1.8.0"]
                 [org.clojure/java.jdbc "0.4.2"]
                 [com.h2database/h2 "1.4.191"]
                 [yesql "0.5.2"]
                 [environ"1.0.3"]
                 [boot-environ "1.0.3"]
                 [org.danielsz/system "0.3.2-SNAPSHOT"]
                 [ring/ring-core "1.4.0"]
                 [ring/ring-defaults "0.1.5"]
                 [ring/ring-jetty-adapter "1.4.0"]
                 [ring-middleware-format "0.7.0"]
                 [compojure "1.4.0"]
                 [hiccup "1.0.5"]
                 [clj-http "2.0.1"]
                 [org.clojure/data.json "0.2.6"]])

(require
 '[adzerk.boot-reload    :refer [reload]]
 '[example.systems :refer [dev-system prod-system]]
 '[environ.boot :refer [environ]] 
 '[system.boot :refer [system run]])

(deftask dev
  "Run a restartable system in the Repl"
  []
  (comp
   (environ :env {:http-port "3025"
                  :imdb-key "348b843dca6130f34597bea34cb95701"})
   (watch :verbose true)
   (system :sys #'dev-system :auto true :files ["handler.clj"])
   (repl :server true)))


(deftask dev-run
  "Run a restartable system in the Repl"
  []
  (comp
   (environ :env {:http-port "3025"
                  :imdb-key "348b843dca6130f34597bea34cb95701"})
   (run :main-namespace "example.core" :arguments [#'dev-system])
   (wait)))

(deftask prod-run
  "Run a restartable system in the Repl"
  []
  (comp
   (environ :env {:http-port "8008"
                  :imdb-key "348b843dca6130f34597bea34cb95701"})
   (run :main-namespace "example.core" :arguments [#'prod-system])
   (wait)))
