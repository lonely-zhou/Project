export default (title: string, text: string, type: string) => {
  const eleLink = document.createElement('a');
  eleLink.download = `${title}.${type}`;
  eleLink.style.display = 'none';
  // 字符内容转变成blob地址
  const blob = new Blob([text]);
  eleLink.href = URL.createObjectURL(blob);
  // 触发点击
  document.body.appendChild(eleLink);
  eleLink.click();
  // 然后移除
  document.body.removeChild(eleLink);
};
