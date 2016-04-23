(ns moodcast.handler
  (:require [compojure.core :refer [GET defroutes]]
            [compojure.route :refer [not-found resources]]
            [ring.util.response :refer [file-response]]))

(defroutes handler
  (GET "/" [] (file-response "index.html" {:root "resources/public"}))
  (resources "/"))
