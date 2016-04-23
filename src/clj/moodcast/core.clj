(ns moodcast.core
  (:require [moodcast.handler :refer [app]]
            [ring.adapter.jetty :refer [run-jetty]])
  (:gen-class))

 (defn -main [& args]
   (let [port (Integer/parseInt (or (System/getenv "PORT") "80"))]
     (run-jetty app {:port port :join? false})))
