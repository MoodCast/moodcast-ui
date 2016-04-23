(ns moodcast.db
  (:require [moodcast.util :refer [index-by]]))

(def default-db
  {:name "MoodCast"
   :people {}
   :svgs {}})
