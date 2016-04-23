(ns moodcast.util)

(defn index-by [id coll]
  (into {} (map (fn [[k vs]] [k (first vs)]) (group-by id coll))))
