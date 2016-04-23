(ns moodcast.svg
  (:require [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]))

(defn load-svg [filename]
  (go (let [response (<! (http/get filename :timeout 500})
            svg  (if (and response (:body response))
                   (:body response)
                   "not-found")]
        (re-frame/dispatch [:load-svg svg]))))
