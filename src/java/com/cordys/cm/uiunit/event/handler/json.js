

Object.toJSON = function(object)
{
   var type = typeof object;
   switch(type) {
     case 'undefined':
     case 'function':
     case 'unknown': return;
     case 'boolean': return object.toString();
   }
   if (object === null) return 'null';
   if (object.toJSON) return object.toJSON();
   if (object.ownerDocument === document) return;
   if (typeof(object['innerHTML']) !== 'undefined' && typeof(object['uniqueID']) !== 'undefined') return;
   if (typeof(object['body']) !== 'undefined' && typeof(object['uniqueID']) !== 'undefined') return;
   if (typeof(object['nodeTypeString']) !== 'undefined' && typeof(object['xml']) !== 'undefined') return object.xml;
   
   var results = [];
   for (var property in object) {
     if(typeof(object[property]) !== 'unknown')
     {
     	var value = Object.toJSON(object[property]);
     	if (value !== undefined)
       		results.push(property.toJSON() + ':' + value);
     }
   }
   return '{' + results.join(',') + '}';
};

Number.prototype.toJSON = function()
{
    return isFinite(this) ? this.toString() : 'null';
};

Date.prototype.toJSON = function() {
  return '"' + this.getFullYear() + '-' +
    (this.getMonth() + 1).toPaddedString(2) + '-' +
    this.getDate().toPaddedString(2) + 'T' +
    this.getHours().toPaddedString(2) + ':' +
    this.getMinutes().toPaddedString(2) + ':' +
    this.getSeconds().toPaddedString(2) + '"';
};

String.interpret = function(value) {
    return value === null ? '' : String(value);
};

String.specialChar = {
    '\b': '\\b',
    '\t': '\\t',
    '\n': '\\n',
    '\f': '\\f',
    '\r': '\\r',
    '\\': '\\\\'
};

String.prototype.toJSON = function()
{
    return this.inspect(true);
};

String.prototype.gsub = function(pattern, replacement) {
    var result = '', source = this, match;
    replacement = arguments.callee.prepareReplacement(replacement);

    while (source.length > 0) {
      if (match = source.match(pattern)) {
        result += source.slice(0, match.index);
        result += String.interpret(replacement(match));
        source  = source.slice(match.index + match[0].length);
      } else {
        result += source, source = '';
      }
    }
    return result;
};
 
String.prototype.inspect = function(useDoubleQuotes) {
    var escapedString = this.gsub(/[\x00-\x1f\\]/, function(match) {
      var character = String.specialChar[match[0]];
      return character ? character : '\\u00' + match[0].charCodeAt().toPaddedString(2, 16);
    });
    if (useDoubleQuotes) return '"' + escapedString.replace(/"/g, '\\"') + '"';
    return "'" + escapedString.replace(/'/g, '\\\'') + "'";
};

String.prototype.toJSON = function() {
    return this.inspect(true);
};

String.prototype.evalJSON = function(sanitize) {
    try {
      if (!sanitize || (/^("(\\.|[^"\\\n\r])*?"|[,:{}\[\]0-9.\-+Eaeflnr-u \n\r\t])+?$/.test(this)))
        return eval('(' + this + ')');
    } catch (e) {}
    throw new SyntaxError('Badly formated JSON string: ' + this.inspect());
};

String.prototype.gsub.prepareReplacement = function(replacement) {
  if (typeof replacement == 'function') return replacement;
  var template = new Template(replacement);
  return function(match) { return template.evaluate(match) };
}

/*Array.prototype.toJSON = function() {
    var results = [];
    debugger; // TODO -> rewrite this in normal Javascript methods
    this.each(function(object) {
      var value = Object.toJSON(object);
      if (value !== undefined) results.push(value);
    });
    return '[' + results.join(',') + ']';
 };*/
 
 
if(typeof(CordysRoot.JSON) == 'undefined')
{
	CordysRoot.JSON = {}
	CordysRoot.JSON.getType = function(object)
	{
		switch (typeof object)
		{
		
			case 'object':
				if (typeof(object['nodeTypeString']) !== 'undefined' && typeof(object['xml']) !== 'undefined')
				{
					return 'xml';
				}
				else
				{
					return 'object';				
				}
                break;
            default:
            	return typeof object;
        }
	}
	
	CordysRoot.JSON.getData = function(object)
	{
		switch (typeof object)
		{
			case 'undefined':
			case 'unknown':
			case 'function':
				return null;
			case 'object':
				return Object.toJSON(object);
            default:
            	return object.toJSON();;
        }
	}
}
