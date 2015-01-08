(defproject om-async "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :jvm-opts ^:replace ["-Xmx1g" "-server"]

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [fogus/ring-edn "0.2.0"]
                 [com.datomic/datomic-free "0.9.4699"]]

  

  :source-paths ["src/clj"]
  :resource-paths ["resources"]

   )
