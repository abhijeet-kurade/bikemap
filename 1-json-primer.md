# JSON Primer

[JSON] (JavaScript Object Notation)
is a *text-based* data interchange format.

[JSON]: https://www.json.org/

Here, we'll show you how to parse a JSON *string*
into an *unstructured object* (aka *dynamic object*)
in a variety of programming languages.

> These operations are straightforward
> in dynamically typed languages like
> Python, Ruby, and JavaScript,
> and harder in statically typed languages,
> such as **Java**, **C#**, and **Go**.
>
> We suggest using ad hoc dynamic objects for this interview,
> even in statically typed languages.
> However, if you prefer to declare classes to represent data,
> go ahead.

Given this JSON *text*:

```json
{
    "foo": {
        "bar": [
            {"nato": "oscar"},
            {"nato": "papa"},
            {"nato": "quebec"}
        ]
    }
}
```

which can also be written on one line as:

```json
{"foo": {"bar": [{"nato": "oscar"}, {"nato": "papa"}, {"nato": "quebec"}]}}
```

We want to parse this string into an object called `data`,
then **extract** a field several levels down:

```js
data["foo"]["bar"][1]["nato"]
```

namely the string value `"papa"`.

We'll also **construct** a new compound object
and **add** it as a subobject of `data`:

```js
data["foo"]["quux"] = {"stuff": "nonsense", "nums": [2.718, 3.142]}
```

which updates the structure to:

```json
{
    "foo": {
        "bar": [
            {"nato": "oscar"},
            {"nato": "papa"},
            {"nato": "quebec"}
        ],
        "quux": {
            "stuff": "nonsense",
            "nums": [
                2.718,
                3.142
            ]
        }
    }
}
```

Note: implementations may sort the fields
(`bar`/`quux` and `stuff`/`nums`) differently
when serializing objects to JSON.

## Sample Code for Unstructured Object Manipulation

You can find working *sample code* for JSON handling
of the above example
in subdirectories of this repo.

* [C++](./cpp/README.md)
* [C#](./csharp/README.md)
* [Go](./go/README.md)
* [Java](./java/README.md)
* [JavaScript](./javascript/README.md)
* [Python](./python/README.md)
* [Ruby](./ruby/README.md)
* [Scala](./scala/README.md)

## Other Languages

* Clojure: Use [data.json]
* Common Lisp: Use [cl-json]
* Kotlin: See [Java](./java/README.md)
* PHP: use [php-json]

[data.json]: http://clojure.github.io/data.json/
[cl-json]: https://common-lisp.net/project/cl-json/cl-json.html
[php-json]: https://www.php.net/manual/en/book.json.php
