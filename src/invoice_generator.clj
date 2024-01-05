(ns invoice-generator
  (:require
    [clojure.spec.alpha :as s]
    [clojure.data.json :as json]))

;; Load the spec from invoice-spec.clj
(load-file "invoice-spec.clj")

(defn load-json [filename]
  (-> filename
      slurp
      (json/read-str {:eof-error? false})))

(defn generate-invoice [json-file]
  (let [invoice-data (load-json json-file)]
    (if (s/valid? ::invoice invoice-data)
      invoice-data
      (throw (ex-info "Generated invoice does not conform to the spec" {:data invoice-data})))))

;; using invoice.json
(def generated-invoice (generate-invoice "invoice.json"))

;; Validate the generated invoice against the spec
(println (s/valid? ::invoice generated-invoice))
