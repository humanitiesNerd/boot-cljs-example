(set-env!
 :src-paths    #{"src/cljs"}
 :rsc-paths    #{"resources/public" }
 :dependencies '[[adzerk/boot-cljs      "0.0-2371-25" :scope "test"]
                 [adzerk/boot-cljs-repl "0.1.6"       :scope "test"]
                 [adzerk/boot-reload    "0.1.6"       :scope "test"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [om "0.5.3"]
                 [om-sync "0.1.1"]
                 [fogus/ring-edn "0.2.0"]
                 ;;[shodan "0.4.1"]
                 ])

(require
 '[adzerk.boot-cljs      :refer :all]
 '[adzerk.boot-cljs-repl :refer :all]
 '[adzerk.boot-reload    :refer :all]
 '[boot.pod              :as pod]
 '[boot.util             :as util]
 '[boot.core             :as core])

(deftask serve-with-datomic
  "Start a web server on localhost and serve the app.
   The web server calls a datomic service that should be running already.

   Listens on port 3000 by default."
  [p port PORT int "The port to listen on."]
  (let [worker (pod/make-pod {:dependencies '[[ring/ring-jetty-adapter "1.3.1"]
                                              [compojure "1.2.1"]
                                              [fogus/ring-edn "0.2.0"]
                                              [com.datomic/datomic-free "0.9.4699"]]
                              :src-paths #{"src/clj"}}
                             )
        port   (or port 3000)]
    (core/cleanup
     (util/info "<< stopping Jetty... >>")
     (pod/eval-in worker (.stop server)))
    (with-pre-wrap
      (pod/eval-in worker
        (require '[ring.adapter.jetty :refer [run-jetty]]
                 '[compojure.handler  :refer [site]]
                 '[compojure.route    :refer [files]]
                 '[om-async.core      :refer [app]])
        (def server (run-jetty #'app {:port ~port :join? false})))
      (util/info "<< started web server on http://localhost:%d  >>\n" port ))))
