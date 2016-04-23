(ns moodcast.db
  (:require [moodcast.util :refer [index-by]]))

(def default-db
  {:name "MoodCast"
   :people {:ilkka {:avatar :sharkman :state :happy :position [300 100]}}
   :svgs {}})
